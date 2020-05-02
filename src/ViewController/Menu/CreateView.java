package ViewController.Menu;

import Maps.MapTools;
import ViewController.View;
import ViewController.ViewManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;

public class CreateView extends View {
    private Scene scene;
    private GridPane root;

    public CreateView(ViewManager viewManager) {
        super(viewManager);

        this.root = new GridPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");

        this.scene = new Scene(this.root);

        // port
        Text portText = new Text();
        portText.setFont(Font.font("Upheaval TT (BRK)", 60));
        portText.setText("Server port: ");
        portText.setFill(Color.WHITE);
        TextField portTextField = new TextField();
        portTextField.setFont(Font.font("Upheaval TT (BRK)", 40));
        portTextField.setText(Integer.toString(viewManager.getParameters().port));
        portTextField.setAlignment(Pos.CENTER);
        portTextField.setMaxWidth(200);
        portTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    portTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (portTextField.getText().length() > 6) {
                    String s = portTextField.getText().substring(0, 6);
                    portTextField.setText(s);
                }
            }
        });
        this.root.add(portText, 0, 0);
        this.root.add(portTextField, 1, 0);

        // player count
        Text playerCountText = new Text();
        playerCountText.setFont(Font.font("Upheaval TT (BRK)", 60));
        playerCountText.setText("Player count: ");
        playerCountText.setFill(Color.WHITE);
        TextField playerCountTextField = new TextField();
        playerCountTextField.setFont(Font.font("Upheaval TT (BRK)", 40));
        playerCountTextField.setText(Integer.toString(2));
        playerCountTextField.setAlignment(Pos.CENTER);
        playerCountTextField.setMaxWidth(200);
        playerCountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    playerCountTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (playerCountTextField.getText().length() > 1) {
                    String s = playerCountTextField.getText().substring(0, 1);
                    playerCountTextField.setText(s);
                }
                if (Integer.parseInt(playerCountTextField.getText()) < 1) {
                    playerCountTextField.setText(Integer.toString(1));
                } else if (Integer.parseInt(playerCountTextField.getText()) > 4) {
                    playerCountTextField.setText(Integer.toString(4));
                }
            }
        });
        this.root.add(playerCountText, 0, 1);
        this.root.add(playerCountTextField, 1, 1);

        // Maps
        int currentRowIndex = 0;
        for (String map : MapTools.enumerateMaps()) {
            Button mapButton = new Button();
            mapButton.setText(map.split("\\.")[0]);
            mapButton.setFont(Font.font("Upheaval TT (BRK)", 20));
            mapButton.setStyle("-fx-background-color: transparent;");
            mapButton.setTextFill(Color.WHITE);
            mapButton.setTextAlignment(TextAlignment.CENTER);
            mapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    viewManager.getParameters().map = map;
                    viewManager.getParameters().solo = false;
                    viewManager.getParameters().port = Integer.parseInt(portTextField.getText());
                    viewManager.getParameters().playerCount = Integer.parseInt(playerCountTextField.getText());
                    viewManager.setView(ViewManager.State.GAME);
                }
            });
            this.root.add(mapButton, 0, currentRowIndex, 2, 1);
            currentRowIndex++;
        }

        // Back button
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setTextFill(Color.WHITE);
        backButton.setPadding(new Insets(100, 0, 0, 0));
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.JOIN_CREATE_SERVER);
            }
        });
        this.root.add(backButton, 0, currentRowIndex);

        this.root.requestFocus();
    }

    @Override
    public void initialize() {}
    @Override
    public void terminate() {}
    @Override
    public Scene getScene() {
        return this.scene;
    }
    public void onSizeChanged(int width, int height) {}
    public void onWindowEvent(WindowEvent event) {}
}
