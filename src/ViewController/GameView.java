/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class GameView implements MapListener, CellListener, EntityListener {


    private FlowPane playerInfoPane;
    private PlayerInfo[] playerInfos;
    private CellLayer cellLayer;
    private EntityLayer entityLayer;
    private GridPane gridPane;

    public GameView(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        this.playerInfoPane = new FlowPane();
        this.playerInfoPane.setAlignment(Pos.CENTER);
        this.playerInfoPane.setHgap(40.0);
        this.playerInfoPane.setPadding(new Insets(5, 5, 5, 5));

        this.playerInfos = new PlayerInfo[10];

        this.cellLayer = new CellLayer();

        this.entityLayer = new EntityLayer(cellLayer);

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.add(this.playerInfoPane, 0, 0);
        this.gridPane.add(this.cellLayer, 0, 1);
        root.getChildren().add(this.gridPane);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        stage.setTitle("Pacman est un très Beaujeu !");
        stage.setScene(scene);
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.show();

        root.requestFocus();

        // Create game controller and bind events from view
        GameController gameController = new GameController(this);
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gameController.handle(event);
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                gameController.handle(event);
            }
        });
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double factorY = (stage.getHeight() - 30) / ((double)this.cellLayer.getGridHeight() + 120);
            double factorX = stage.getWidth() / (double)this.cellLayer.getGridWidth();
            double factor = Math.min(2.0, Math.min(factorY, factorX));
            root.setScaleX(factor);
            root.setScaleY(factor);
        };
        stageSizeListener.changed(null, null, null);
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    public void setController(PacmanController controller) {
        if (cellLayer.getGridHeight() >= cellLayer.getGridWidth()) {
            this.playerInfos[controller.getIndex()] = new PlayerInfo();
            this.playerInfos[controller.getIndex()].setController(controller);
            this.playerInfoPane.getChildren().add(this.playerInfos[controller.getIndex()]);
        }
    }

    @Override
    public void mapUpdated(Cell[][] cells) {
        this.cellLayer.updateMap(cells);
    }

    @Override
    public void cellUpdated(Cell cell, Point position) {
        this.cellLayer.updateCell(cell, position);
    }

    @Override
    public void entityUpdated(Entity entity, Point position) {
        entityLayer.updateEntity(entity, position);
        for (PlayerInfo playerInfo : this.playerInfos) {
            if (playerInfo != null) {
                playerInfo.updateEntity(entity, position);
            }
        }
    }
}
