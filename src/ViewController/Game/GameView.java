/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController.Game;

import Model.*;

import Network.Client;
import Network.MessageListener;
import Network.Messages.*;
import Network.Server;
import ViewController.View;
import ViewController.ViewManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Inet4Address;

public class GameView extends View implements MessageListener {
    private Scene scene;
    private StackPane root;
    private FlowPane playerInfoPane;
    private PlayerInfo[] playerInfos;
    private CellLayer cellLayer;
    private EntityLayer entityLayer;
    private GameStateLayer gameStateLayer;
    private GridPane gridPane;

    private Server server;
    private Client client;

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
        this.gameStateLayer = new GameStateLayer();

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.add(this.playerInfoPane, 0, 0);
        this.gridPane.add(this.cellLayer, 0, 1);
        this.root.getChildren().add(this.gridPane);
        this.root.getChildren().add(this.gameStateLayer);

        this.scene = new Scene(root);
        this.scene.setFill(Color.BLACK);

        this.root.requestFocus();

        this.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.Q || code == KeyCode.LEFT) {
                    client.send(Direction.LEFT);
                } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
                    client.send(Direction.RIGHT);
                } else if (code == KeyCode.Z || code == KeyCode.UP) {
                    client.send(Direction.UP);
                } else if (code == KeyCode.S || code == KeyCode.DOWN) {
                    client.send(Direction.DOWN);
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    viewManager.setView(ViewManager.State.MAIN);
                }
            }
        });
    }

    @Override
    public void initialize() {
        try {
            if (this.viewManager.getParameters().solo) {
                this.server = new Server();
                this.server.open(55555, viewManager.getParameters().map, 1);
                this.client = new Client(this);
                this.client.connect((Inet4Address) Inet4Address.getLocalHost(), 55555);
            } else {
                if (this.viewManager.getParameters().address != null) { // join
                    this.client = new Client(this);
                    this.client.connect(viewManager.getParameters().address, viewManager.getParameters().port);
                } else { // create
                    this.server = new Server();
                    this.server.open(
                            this.viewManager.getParameters().port,
                            this.viewManager.getParameters().map,
                            this.viewManager.getParameters().playerCount);
                    this.client = new Client(this);
                    this.client.connect((Inet4Address)Inet4Address.getLocalHost(), this.viewManager.getParameters().port);
                }
            }
        } catch (IOException e) {
            this.viewManager.setView(ViewManager.State.MAIN);
        }
    }

    @Override
    public void terminate() {
        if (this.server != null) this.server.close();
        if (this.client != null) this.client.disconnect();
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

    @Override
    public void onMapMessage(MapMessage message) {
        Platform.runLater(() -> {
            this.cellLayer.updateMap(message);
            this.viewManager.changed(null, null, null);
        });
    }

    @Override
    public void onCellMessage(CellMessage message) {
        Platform.runLater(() -> {
            this.cellLayer.updateCell(message);
        });
    }

    @Override
    public void onEntityMessage(EntityMessage message) {
        this.entityLayer.updateEntity(message);
        if (message instanceof PacmanMessage) {
            PacmanMessage pacmanMessage = (PacmanMessage)message;
            if (this.playerInfos[pacmanMessage.controllerId] == null) {
                if (this.cellLayer.getGridHeight() >= this.cellLayer.getGridWidth()) {
                    this.playerInfos[pacmanMessage.controllerId] = new PlayerInfo(pacmanMessage.controllerId, client.getControllerId());
                    Platform.runLater(() -> {
                        this.playerInfoPane.getChildren().add(this.playerInfos[pacmanMessage.controllerId]);
                    });
                }
            } else {
                this.playerInfos[pacmanMessage.controllerId].updateEntity(pacmanMessage);
            }
        }
    }

    @Override
    public void onGameStateMessage(GameStateMessage message) {
        Platform.runLater(() -> {
            this.gameStateLayer.updateGameState(message);
        });
    }
}
