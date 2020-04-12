package Network;

import Model.*;
import Network.Messages.CellMessage;
import Network.Messages.GhostMessage;
import Network.Messages.Message;
import Network.Messages.PacmanMessage;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements MapListener, CellListener, EntityListener {

    private final static int PORT = 55555;

    private Map<Entity, Integer> entities;
    private LinkedList<Message> messages;
    private int ids;

    private ServerSocket socket;
    private LinkedList<Socket> clients;

    public Server() {
        this.entities = new HashMap<>();
        this.messages = new LinkedList<>();
        this.ids = 0;
    }

    public void create() {
        try {
            this.socket = new ServerSocket(55555);
            Thread messageThread = new Thread(() -> {messageRoutine();});
            messageThread.start();
            Thread connectionThread = new Thread(() -> {connectionRoutine();});
            connectionThread.start();
        } catch (IOException e) {
            System.err.println("Failed to create server.");
        }
    }

    public void connectionRoutine() {
        boolean running = true;
        while (running) {
            try {
                Socket client = this.socket.accept();

                // Negotiate with model

                if (true) {
                    synchronized (this.clients) {
                        this.clients.add(client);
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    public void messageRoutine() {
        try {
            boolean running = true;
            while (running) {
                Message message = null;

                synchronized (this.messages) {
                    while (this.messages.isEmpty() && running) {
                        this.messages.wait();
                    }

                    if (running) {
                        message = this.messages.pop();
                    }
                }

                if (message != null) {
                    this.sendToAll(message.toString());
                }
            }

            synchronized (this.socket) {
                try {
                    this.socket.close();
                } catch (IOException e) { }
            }
        } catch (InterruptedException e) { }
    }

    private void sendToAll(String code) {
        System.out.println("sending to all: " + code);
        synchronized (this.clients) {
            for (Socket client : this.clients) {
                try {
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());
                    output.writeUTF(code);
                    output.flush();
                } catch (IOException e) {
                    System.err.println("Failed to send message. Disconnecting client.");
                    try { client.close(); } catch (IOException ioe) {}
                    this.clients.remove(client);
                }
            }
        }
    }

    @Override
    public void mapUpdated(Cell[][] cells) {

    }
    @Override
    public void cellUpdated(Cell cell, Point position) {
        this.messages.add(new CellMessage(cell, position));
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
