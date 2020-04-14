package Network;

import Model.*;
import Network.Messages.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements MapListener, CellListener, EntityListener {
    private class ClientThread extends Thread {
        private Socket socket;
        private PacmanController controller;
        public ClientThread(Socket socket, PacmanController controller) {
            this.socket = socket;
            this.controller = controller;
        }
        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                while(true) {
                    String line = input.readLine();
                    if (line != null) {
                        switch (line) {
                            case "up":
                                this.controller.up();
                                break;
                            case "down":
                                this.controller.down();
                                break;
                            case "left":
                                this.controller.left();
                                break;
                            case "right":
                                this.controller.right();
                                break;
                        }
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    private ServerSocket socket;
    private Map<Entity, Integer> entities;
    private LinkedList<Message> messages;
    private int playerCount;
    private int ids;
    private String map;

    private Thread thread;
    private ArrayList<ClientThread> clientThreads;
    private ArrayList<Socket> clientSockets;

    private Game game;

    public Server() {
        this.entities = new HashMap<>();
        this.messages = new LinkedList<>();
        this.playerCount = 0;
        this.ids = 0;

        this.clientSockets = new ArrayList<>();
        this.clientThreads = new ArrayList<>();
    }

    public void open(int port, String map, int playerCount) {
        try {
            this.socket = new ServerSocket(port);
            this.playerCount = playerCount;
            this.map = map;

            this.game = new Game();
            this.game.setMapListener(this);
            this.game.setCellListener(this);
            this.game.setEntityListener(this);

            this.thread = new Thread(() -> {routine();});
            this.thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.game.stop();
                this.thread.interrupt();

                for (ClientThread clientThread : this.clientThreads) clientThread.interrupt();
                for (Socket socket : this.clientSockets) socket.close();
                this.clientThreads.clear();
                this.clientSockets.clear();
            }
        } catch (IOException e) {}
    }

    private void routine() {
        this.clientSockets = new ArrayList<>();

        try {
            try {
                while (this.clientSockets.size() < this.playerCount) {
                    Socket socket = this.socket.accept();
                    System.out.println("new connection from " + socket.toString());
                    this.clientSockets.add(socket);
                }
            } catch (IOException e) {}

            // load map (send to all clients)
            this.game.loadMap(this.map);

            // binding clients to controllers (require map being loaded)
            for (int i = 0; i < this.clientSockets.size(); i++) {
                Socket socket = this.clientSockets.get(i);
                PacmanController controller = new PacmanController();
                this.game.setPacmanController(controller, i);
                ClientThread clientThread = new ClientThread(socket, controller);
                this.clientThreads.add(clientThread);
                clientThread.start();
            }

            // start game
            this.game.start();

            // loop
            while (true) {
                Message message = null;
                synchronized (this.messages) {
                    while (this.messages.isEmpty()) this.messages.wait();
                    message = this.messages.pop();
                }
                if (message != null) this.sendToAll(message.toString());
            }
        } catch (InterruptedException e) {}
    }

    private void sendToAll(String code) {
        synchronized (this.clientSockets) {
            for (Socket client : this.clientSockets) {
                try {
                    PrintWriter printer = new PrintWriter(client.getOutputStream());
                    printer.println(code);
                    printer.flush();
                } catch (IOException e) {
                    System.err.println("Failed to send message. Disconnecting client.");
                    try { client.close(); } catch (IOException ioe) {}
                    this.clientSockets.remove(client);
                }
            }
        }
    }

    @Override
    public void mapUpdated(Cell[][] cells) {
        synchronized (this.messages) {
            this.messages.add(new MapMessage(cells));
            for (int x = 0; x < cells.length; x++) {
                for (int y = 0; y < cells[0].length; y++) {
                    this.cellUpdated(cells[x][y], new Point(x, y));
                }
            }
        }
    }
    @Override
    public void cellUpdated(Cell cell, Point position) {
        synchronized (this.messages) {
            if (cell instanceof Wall) {
                this.messages.add(new WallMessage((Wall)cell, position));
            } else if (cell instanceof Door) {
                this.messages.add(new DoorMessage((Door)cell, position));
            } else if (cell instanceof Floor) {
                this.messages.add(new FloorMessage((Floor)cell, position));
            }
        }
    }
    @Override
    public void entityUpdated(Entity entity, Point position) {
        Integer id = this.entities.get(entity);
        if (id == null) {
            id = this.ids++;
            this.entities.put(entity, id);
        }

        synchronized (this.messages) {
            if (entity instanceof Pacman) {
                this.messages.add(new PacmanMessage((Pacman)entity, position, id));
            } else if (entity instanceof Ghost) {
                this.messages.add(new GhostMessage((Ghost)entity, position, id));
            }
            this.messages.notify();
        }
    }
}
