package ViewController.Game;

import Network.Messages.GameStateMessage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStateLayer extends GridPane {
    private Text text;

    public GameStateLayer() {
        this.setMaxWidth(100);
        this.setMaxHeight(70);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5.0);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: white; -fx-border-radius: 5.0; -fx-border-width: 3.0");

        this.text = new Text();
        this.text.setFill(Color.WHITE);
        this.text.setFont(Font.font("Upheaval TT (BRK)", 35));
        this.text.toFront();

        this.add(this.text, 0, 0);
    }

    public void updateGameState(GameStateMessage message) {
        if (message.waitingConnection) {
            this.setVisible(true);
            this.text.setText("Waiting players... " + message.currentPlayerCount + "/" + message.totalPlayer);
        } else {
            this.setVisible(false);
        }
    }
}
