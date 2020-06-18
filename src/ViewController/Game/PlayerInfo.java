package ViewController.Game;

import Model.MovableEntity;
import Network.Messages.PacmanMessage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PlayerInfo extends GridPane {
    private Text playerText;
    private Text scoreText;
    private FlowPane flowPane;

    public PlayerInfo(int controllerId, int ownerControllerId) {
        this.setMinWidth(100);
        this.setMinHeight(70);
        if (ownerControllerId == controllerId) {
            this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
        } else {
            this.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
        }

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5.0);

        this.playerText = new Text("Player " + 0);
        this.playerText.setFont(Font.font("Upheaval TT (BRK)", 35));
        this.playerText.setFill(Color.WHITE);
        this.add(playerText, 0, 0);

        this.scoreText = new Text("0");
        this.scoreText.setFont(Font.font("Upheaval TT (BRK)", 30));
        this.scoreText.setFill(Color.WHITE);
        this.add(this.scoreText, 0, 1);

        this.flowPane = new FlowPane();
        this.flowPane.setMaxSize(Sprite.TILE_SIZE * 4, Sprite.TILE_SIZE);
        this.add(this.flowPane, 0, 2);

        this.playerText.setText("Player " + (controllerId + 1));
    }

    public void updateEntity(PacmanMessage message) {
        System.out.println(message.lifes);

        Platform.runLater(() -> {
            this.scoreText.setText(Integer.toString(message.score));
        });

        if (message.entityState == MovableEntity.EntityState.DEAD) {
            Platform.runLater(() -> {
                this.flowPane.getChildren().clear();
            });
        } else {
            Platform.runLater(() -> {
                this.flowPane.getChildren().clear();
                for (int i = 0; i < message.lifes; i++) {
                    Sprite sprite = new Sprite();
                    sprite.setSpriteSheet(PacmanAnimation.imagePacman);
                    sprite.setFrame(0, 0);
                    this.flowPane.getChildren().add(sprite);
                }
            });
        }
    }
}
