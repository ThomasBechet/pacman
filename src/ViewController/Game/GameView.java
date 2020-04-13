/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController.Game;

import Model.*;

import Network.Client;
import Network.MessageListener;
import Network.Messages.CellMessage;
import Network.Messages.EntityMessage;
import Network.Messages.MapMessage;
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

import java.awt.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class GameView extends View implements MessageListener {
    private Scene scene;
    private StackPane root;
    private FlowPane playerInfoPane;
    private PlayerInfo[] playerInfos;
    private CellLayer cellLayer;
    private EntityLayer entityLayer;
    private GridPane gridPane;
    private GameController gameController;

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

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.add(this.playerInfoPane, 0, 0);
        this.gridPane.add(this.cellLayer, 0, 1);
        this.root.getChildren().add(this.gridPane);

        this.scene = new Scene(root);
        this.scene.setFill(Color.BLACK);

        this.root.requestFocus();

        //this.gameController = new GameController(this);
        if (viewManager.getParameters().solo) {
            try {
                this.server = new Server();
                this.server.open(55555, "src/Maps/map2.txt", 1);
                this.client = new Client(this);
                this.client.connect((Inet4Address) Inet4Address.getLocalHost(), 55555);
            } catch (UnknownHostException e) {}
        } else {
            if (viewManager.getParameters().address != null) { // join
                this.client = new Client(this);
                this.client.connect(viewManager.getParameters().address, viewManager.getParameters().port);
            } else { // create
                this.server = new Server();
                this.server.open(
                        viewManager.getParameters().port,
                        viewManager.getParameters().map,
                        viewManager.getParameters().playerCount);
                this.client = new Client(this);
                try {
                    this.client.connect((Inet4Address)Inet4Address.getLocalHost(), viewManager.getParameters().port);
                } catch (UnknownHostException e) {}
            }
        }

        this.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //gameController.handle(event);

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
    public void terminate() {
        //this.gameController.terminate();
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

    public void setController(PacmanController controller) {
        if (cellLayer.getGridHeight() >= cellLayer.getGridWidth()) {
            this.playerInfos[controller.getIndex()] = new PlayerInfo();
            this.playerInfos[controller.getIndex()].setController(controller);
            this.playerInfoPane.getChildren().add(this.playerInfos[controller.getIndex()]);
        }
    }

    @Override
    public void onMapMessage(MapMessage message) {
        Platform.runLater(() -> {
            this.cellLayer.updateMap(message);
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
        entityLayer.updateEntity(message);
        for (PlayerInfo playerInfo : this.playerInfos) {
            if (playerInfo != null) {
                playerInfo.updateEntity(message);
            }
        }
    }
}
