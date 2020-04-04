/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController.Game;

import Model.*;

import ViewController.View;
import ViewController.ViewManager;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

import java.awt.*;

public class GameView extends View implements MapListener, CellListener, EntityListener {
    private Scene scene;
    private StackPane root;
    private FlowPane playerInfoPane;
    private PlayerInfo[] playerInfos;
    private CellLayer cellLayer;
    private EntityLayer entityLayer;
    private GridPane gridPane;
    private GameController gameController;

    public GameView(ViewManager viewManager) {
        super(viewManager);

        this.root = new StackPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-color: black;");

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
        this.root.getChildren().add(this.gridPane);

        this.scene = new Scene(root);
        this.scene.setFill(Color.BLACK);

        this.root.requestFocus();

        this.gameController = new GameController(this);
        this.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gameController.handle(event);
                if (event.getCode() == KeyCode.ESCAPE) {
                    viewManager.setView(ViewManager.State.MAIN);
                }
            }
        });
    }

    @Override
    public void terminate() {
        this.gameController.terminate();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void onSizeChanged(int width, int height) {
        double factorY = ((double)height - 30) / ((double)this.cellLayer.getGridHeight() + 120);
        double factorX = (double)width / (double)this.cellLayer.getGridWidth();
        double factor = Math.min(2.0, Math.min(factorY, factorX));
        root.setScaleX(factor);
        root.setScaleY(factor);
    }
    @Override
    public void onWindowEvent(WindowEvent event) {

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
