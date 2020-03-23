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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class GameView extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        PlayerInfo playerInfo1 = new PlayerInfo();
        PlayerInfo playerInfo2 = new PlayerInfo();
        PlayerInfo playerInfo3 = new PlayerInfo();
        PlayerInfo playerInfo4 = new PlayerInfo();

        Game game = new Game();

        CellLayer cellLayer = new CellLayer();
        game.setMapListener(cellLayer);
        game.setCellListener(cellLayer);
        EntityLayer entityLayer = new EntityLayer(cellLayer);
        game.setEntityListener(new EntityListener() {
            @Override
            public void entityUpdated(Entity entity, Point position) {
                entityLayer.entityUpdated(entity, position);
                playerInfo1.entityUpdated(entity, position);
            }
        });

        game.loadMap("src/Maps/map2.txt");
        PacmanController player1 = new PacmanController();
        PacmanController player2 = new PacmanController();
        PacmanController player3 = new PacmanController();
        PacmanController player4 = new PacmanController();
        game.setPacmanController(player1, PacmanController.PLAYER_1);
        game.setPacmanController(player2, PacmanController.PLAYER_2);
        game.setPacmanController(player3, PacmanController.PLAYER_3);
        game.setPacmanController(player4, PacmanController.PLAYER_4);
        playerInfo1.setController(player1);
        playerInfo2.setController(player2);
        playerInfo3.setController(player3);
        playerInfo4.setController(player4);
        game.start();

        GridPane gridPane = new GridPane();

        Text test1 = new Text("Test1");
        test1.setFill(Color.WHITE);
        test1.setTextAlignment(TextAlignment.RIGHT);
        Text test2 = new Text("Test2");
        test2.setFill(Color.WHITE);
        Text test3 = new Text("Test3");
        test3.setFill(Color.WHITE);

        if (cellLayer.getGridHeight() >= cellLayer.getGridWidth()) {
            gridPane.setAlignment(Pos.CENTER);
            FlowPane flowPane = new FlowPane();

            flowPane.getChildren().add(playerInfo1);
            flowPane.getChildren().add(playerInfo2);
            flowPane.getChildren().add(playerInfo3);
            flowPane.getChildren().add(playerInfo4);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setHgap(40.0);
            flowPane.setPadding(new Insets(5, 5, 5, 5));

            gridPane.add(flowPane, 0, 0);
            gridPane.add(cellLayer, 0, 1);
        } else {
            gridPane.add(playerInfo1, 0, 0);
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
                } else if (code == KeyCode.K) {
                    player1.removeLife();
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
            double factorY = (stage.getHeight() - 30) / ((double)cellLayer.getGridHeight() + 120);
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
