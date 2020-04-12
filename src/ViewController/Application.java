package ViewController;

import Model.Door;
import Model.Pacman;
import Network.Client;
import Network.Server;
import javafx.stage.Stage;

import java.awt.*;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        Server server = new Server();
        server.create();
        server.entityUpdated(new Pacman(null, 3), new Point(4, 4));

        //ViewManager manager = new ViewManager(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
