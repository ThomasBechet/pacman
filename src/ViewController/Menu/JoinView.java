package ViewController.Menu;

import ViewController.View;
import ViewController.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

import java.net.Inet4Address;

public class JoinView extends View {
    private Scene scene;
    private GridPane root;

    public JoinView(ViewManager viewManager) {
        super(viewManager);

        this.root = new GridPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");

        this.scene = new Scene(this.root);

        // Address
        TextField ipTextField = new TextField();
        ipTextField.setText("0.0.0.0");
        ipTextField.setFont(Font.font("Upheaval TT (BRK)", 60));
        this.root.add(ipTextField, 0, 0, 2, 1);

        Button joinButton = new Button();
        joinButton.setText("Join");
        joinButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        joinButton.setStyle("-fx-background-color: transparent;");
        joinButton.setTextFill(Color.WHITE);
        joinButton.setAlignment(Pos.CENTER);
        joinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String[] parts = ipTextField.getCharacters().toString().split(":");
                    viewManager.getParameters().address = (Inet4Address)Inet4Address.getByName(parts[0]);
                    if (parts.length > 1) {
                        viewManager.getParameters().port = Integer.parseInt(parts[1]);
                    } else {
                        viewManager.getParameters().port = 55555;
                    }

                    viewManager.setView(ViewManager.State.GAME);
                } catch(Exception e) {
                    ipTextField.setText("Invalid format");
                }
            }
        });
        this.root.add(joinButton, 1, 1, 1, 1);

        // Back
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.JOIN_CREATE_SERVER);
            }
        });
        this.root.add(backButton, 0, 1, 1, 1);

        this.root.requestFocus();
    }

    @Override
    public void terminate() {}
    @Override
    public Scene getScene() {
        return this.scene;
    }
    public void onSizeChanged(int width, int height) {}
    public void onWindowEvent(WindowEvent event) {}
}
