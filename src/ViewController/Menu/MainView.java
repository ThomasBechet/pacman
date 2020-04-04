package ViewController.Menu;

import ViewController.View;
import ViewController.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.util.Collections;

public class MainView extends View {
    private Scene scene;
    private FlowPane root;

    public MainView(ViewManager viewManager) {
        super(viewManager);

        this.root = new FlowPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setColumnHalignment(HPos.CENTER);
        this.root.setOrientation(Orientation.VERTICAL);
        this.root.setVgap(30.0);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");

        this.scene = new Scene(this.root);

        // Title text
        Text text = new Text();
        text.setText("PACMAN");
        text.setFont(Font.font("Edit Undo Line BRK", 170));
        text.setFill(Color.YELLOW);
        this.root.getChildren().add(text);

        // Start button
        Button soloButton = new Button();
        soloButton.setText("Solo");
        soloButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        soloButton.setStyle("-fx-background-color: transparent;");
        soloButton.setTextFill(Color.WHITE);
        soloButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.MAP_SELECTION);
            }
        });
        this.root.getChildren().add(soloButton);

        // Multiplayer button
        Button multiplayerButton = new Button();
        multiplayerButton.setText("Multiplayer");
        multiplayerButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        multiplayerButton.setStyle("-fx-background-color: transparent;");
        multiplayerButton.setTextFill(Color.WHITE);
        multiplayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.JOIN_CREATE_SERVER);
            }
        });
        this.root.getChildren().add(multiplayerButton);

        // Quit button
        Button quitButton = new Button();
        quitButton.setText("Quit");
        quitButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        quitButton.setStyle("-fx-background-color: transparent;");
        quitButton.setTextFill(Color.WHITE);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.terminate();
            }
        });
        this.root.getChildren().add(quitButton);
        this.root.requestFocus();

        this.viewManager.getParameters().reset();
    }

    @Override
    public void terminate() {

    }
    @Override
    public Scene getScene() {
        return this.scene;
    }
    public void onSizeChanged(int width, int height) {}
    public void onWindowEvent(WindowEvent event) {}
}
