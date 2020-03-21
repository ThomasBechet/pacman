/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author frederic.armetta
 */
public class SimpleVC extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Game game = new Game();

        CellLayer cellLayer = new CellLayer(root);
        game.setMapListener(cellLayer);
        game.setCellListener(cellLayer);
        EntityLayer entityLayer = new EntityLayer(cellLayer);
        game.setEntityListener(entityLayer);

        game.loadMap("src/Maps/map1.txt");
        PacmanController player1 = new PacmanController();
        game.setPacmanController(player1, PacmanController.PLAYER_1);

        game.start();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Pacman est un très Beaujeu !");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.Q || code == KeyCode.LEFT) {
                    player1.left();
                } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                    player1.right();
                } else if (code == KeyCode.Z || code == KeyCode.UP) {
                    player1.up();
                } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                    player1.down();
                }
            }
        });

        root.requestFocus();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                game.stop();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
