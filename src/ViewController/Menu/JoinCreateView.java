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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

public class JoinCreateView extends View {
    private Scene scene;
    private FlowPane root;

    public JoinCreateView(ViewManager viewManager) {
        super(viewManager);

        this.root = new FlowPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setColumnHalignment(HPos.CENTER);
        this.root.setOrientation(Orientation.VERTICAL);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");

        this.scene = new Scene(this.root);

        // Join
        Button joinButton = new Button();
        joinButton.setText("Join");
        joinButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        joinButton.setStyle("-fx-background-color: transparent;");
        joinButton.setTextFill(Color.WHITE);
        joinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.JOIN);
            }
        });
        this.root.getChildren().add(joinButton);

        // Create
        Button createButton = new Button();
        createButton.setText("Create");
        createButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        createButton.setStyle("-fx-background-color: transparent;");
        createButton.setTextFill(Color.WHITE);
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.CREATE);
            }
        });
        this.root.getChildren().add(createButton);

        // Back
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setFont(Font.font("Upheaval TT (BRK)", 60));
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.MAIN);
            }
        });
        this.root.getChildren().add(backButton);

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
