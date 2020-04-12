package Network;

import Network.Messages.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;

public class Client {
    private Socket socket;

    public void connect(Inet4Address address, int port) {
        try {
            this.socket = new Socket(address, port);
            Thread thread = new Thread(() -> {routine();});
            thread.start();
        } catch(IOException e) {

        }
    }

    public void routine() {
        try {
            boolean running = true;
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while(running) {
                String line = input.readLine();
                this.dispatch(line);
            }
        } catch(IOException e) {

        }
    }

    public void dispatch(String code) {
        String[] keyMessage = code.split("@");
        Parameter[] parameters = Parameter.parse(keyMessage[1]);

        Message message = new Message();
        switch (keyMessage[0]) {
            case "wall":
                message = new WallMessage(parameters);
                break;
            case "floor":
                message = new FloorMessage(parameters);
                break;
            case "door":
                message = new DoorMessage(parameters);
                break;
            case "ghost":
                message = new GhostMessage(parameters);
                break;
            case "map":
                message = new MapMessage(parameters);
                break;
            case "pacman":
                message = new PacmanMessage(parameters);
                break;
        }

        System.out.println(message);
    }
}
