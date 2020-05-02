package ViewController.Menu;

import Maps.MapTools;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

public class SoloView extends View {
    private Scene scene;
    private FlowPane root;

    public SoloView(ViewManager viewManager) {
        super(viewManager);

        this.root = new FlowPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setColumnHalignment(HPos.CENTER);
        this.root.setOrientation(Orientation.VERTICAL);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");

        this.scene = new Scene(this.root);

        // Maps buttons
        for (String map : MapTools.enumerateMaps()) {
            Button mapButton = new Button();
            mapButton.setText(map);
            mapButton.setFont(Font.font("Upheaval TT (BRK)", 30));
            mapButton.setStyle("-fx-background-color: transparent;");
            mapButton.setTextFill(Color.WHITE);
            mapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    viewManager.getParameters().map = map;
                    viewManager.setView(ViewManager.State.GAME);
                }
            });
            this.root.getChildren().add(mapButton);
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
