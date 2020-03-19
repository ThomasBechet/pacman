/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
        Image imagePacman = new Image("Images/Pacman.png");

        StackPane root = new StackPane();
        GridPane grid = new GridPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(grid);

        Game game = new Game();

        CellGrid cellGrid = new CellGrid(game, grid);
        Map<Entity, ImageView> images = new HashMap<>();

        game.setEntityListener(new EntityListener() {
            @Override
            public void entityUpdated(Entity entity, Point position) {
                ImageView imageView = images.get(entity);
                if (imageView == null) {
                    imageView = new ImageView();
                    grid.getChildren().add(imageView);
                    images.put(entity, imageView);
                    imageView.toBack();

                }

                imageView.toFront();
                imageView.setTranslateX(position.x * 18.0f);
                imageView.setTranslateY(position.y * 19.0f);

                if (entity instanceof Pacman) {
                    imageView.setImage(imagePacman);
                } else if (entity instanceof Ghost) {
                    // TODO
                }
            }
        });

        game.loadMap("src/Maps/map_old.txt");
        PacmanController pacmanController = new PacmanController();
        game.setPacmanController(pacmanController, PacmanController.PLAYER_1);
        game.start();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.Q || code == KeyCode.LEFT) {
                    pacmanController.left();
                } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                    pacmanController.right();
                } else if (code == KeyCode.Z || code == KeyCode.UP) {
                    pacmanController.up();
                } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                    pacmanController.down();
                }
            }
        });

        grid.requestFocus();

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
