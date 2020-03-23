/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Startup extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

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
                } else if (code == KeyCode.X) {
                    player1.inceaseSpeed();
                } else if (code == KeyCode.W) {
                    player1.decreaseSpeed();
                }
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                game.stop();
            }
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        root.setScaleX(2.0);
        root.setScaleY(2.0);
        stage.setTitle("Pacman est un très Beaujeu !");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setMaximized(true);
        stage.show();
        root.requestFocus();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double factorY = (stage.getHeight() - 30) / (double)cellLayer.getHeight();
            double factorX = stage.getWidth() / (double)cellLayer.getWidth();
            double factor = Math.min(2.0, Math.min(factorY, factorX));
            root.setScaleX(factor);
            root.setScaleY(factor);
        };

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
