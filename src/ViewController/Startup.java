/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Startup extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        Game game = new Game();

        CellLayer cellLayer = new CellLayer();
        game.setMapListener(cellLayer);
        game.setCellListener(cellLayer);
        EntityLayer entityLayer = new EntityLayer(cellLayer);
        game.setEntityListener(entityLayer);

        game.loadMap("src/Maps/map2.txt");
        PacmanController player1 = new PacmanController();
        game.setPacmanController(player1, PacmanController.PLAYER_1);
        game.start();

        GridPane gridPane = new GridPane();

        Text test0 = new Text("Test0");
        test0.setFill(Color.WHITE);
        Text test1 = new Text("Test1");
        test1.setFill(Color.WHITE);
        test1.setTextAlignment(TextAlignment.RIGHT);
        Text test2 = new Text("Test2");
        test2.setFill(Color.WHITE);
        Text test3 = new Text("Test3");
        test3.setFill(Color.WHITE);

        if (cellLayer.getGridHeight() >= cellLayer.getGridWidth()) {
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(test0, 0, 0);
            gridPane.add(test1, 2, 0);
            gridPane.add(test2, 0, 2);
            gridPane.add(test3, 2, 2);
            gridPane.add(cellLayer, 0, 1, 3, 1);

            GridPane.setHalignment(test1, HPos.RIGHT);
            GridPane.setHalignment(test3, HPos.RIGHT);
        } else {
            gridPane.add(test0, 0, 0);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(cellLayer, 1, 0, 1, 1);
        }

        root.getChildren().add(gridPane);
        
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
        stage.setTitle("Pacman est un très Beaujeu !");
        stage.setScene(scene);
        stage.setWidth(1600);
        stage.setHeight(900);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double factorY = (stage.getHeight() - 30) / ((double)cellLayer.getGridHeight() + test0.getFont().getSize() * 2.0);
            double factorX = stage.getWidth() / (double)cellLayer.getGridWidth();
            double factor = Math.min(2.0, Math.min(factorY, factorX));
            root.setScaleX(factor);
            root.setScaleY(factor);
        };
        stageSizeListener.changed(null, null, null);
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
        stage.show();


        root.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
