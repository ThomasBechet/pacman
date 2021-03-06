package ViewController.Game;

import Model.GameState;
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
        } else if (message.countdown > 0) {
            this.setVisible(true);
            this.text.setText("Starting in " + ((message.countdown / 1000) + 1) + " ...");
        } else {
            if (message.flowState == GameState.FlowState.ENDED) {
                if (message.winner == -1) {
                    this.text.setText("Egality !");
                } else {
                    this.text.setText("Player " + (message.winner + 1) + " win !");
                }
                this.setVisible(true);
            } else {
                this.setVisible(false);
            }
        }
    }
}
