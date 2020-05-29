package Network;

import Model.Direction;
import Network.Messages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

public class Client {
    private Socket socket;
    private Thread thread;
    private MessageListener messageListener;
    private int controllerId;

    public Client(MessageListener listener) {
        this.messageListener = listener;
    }

    public int getControllerId() {
        return this.controllerId;
    }

    public void connect(Inet4Address address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.thread = new Thread(() -> {routine();});
        this.thread.start();
    }
    public void disconnect() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
                this.thread.join();
            }
        } catch (IOException e) {
            System.err.println("Failed to disconnect client: socket error.");
        } catch (InterruptedException e) {
            System.err.println("Failed to disconnect client: thread error.");
        }
    }

    /**
     * Send move instruction to the server
     * @param direction wanted direction
     */
    public void send(Direction direction) {
        if (this.socket != null) {
            try {
                PrintWriter writer = new PrintWriter(this.socket.getOutputStream());
                switch (direction) {
                    case UP:
                        writer.write("up\n");
                        break;
                    case DOWN:
                        writer.write("down\n");
                        break;
                    case LEFT:
                        writer.write("left\n");
                        break;
                    case RIGHT:
                        writer.write("right\n");
                        break;
                }
                writer.flush();
            } catch (IOException e) {}
        }
    }

    /**
     * Thread client routine
     */
    public void routine() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while(this.socket != null) {
                // Receive message from the server
                String line = input.readLine();
                if (line != null) {
                    this.dispatch(line);
                }
            }
        } catch(IOException e) {}
    }

    /**
     * Dispatch server message
     * @param code received code
     */
    public void dispatch(String code) {
        String[] keyMessage = code.split("@");
        Parameter[] parameters = Parameter.parse(keyMessage[1]);

        // Read the message code to rebuild to good message
        switch (keyMessage[0]) {
            case "controller":
                this.controllerId = Integer.parseInt(parameters[0].value);
                break;
            case "wall":
                this.messageListener.onCellMessage(new WallMessage(parameters));
                break;
            case "floor":
                this.messageListener.onCellMessage(new FloorMessage(parameters));
                break;
            case "door":
                this.messageListener.onCellMessage(new DoorMessage(parameters));
                break;
            case "ghost":
                this.messageListener.onEntityMessage(new GhostMessage(parameters));
                break;
            case "map":
                this.messageListener.onMapMessage(new MapMessage(parameters));
                break;
            case "pacman":
                this.messageListener.onEntityMessage(new PacmanMessage(parameters));
                break;
            case "gamestate":
                this.messageListener.onGameStateMessage(new GameStateMessage(parameters));
                break;
        }
    }
}
