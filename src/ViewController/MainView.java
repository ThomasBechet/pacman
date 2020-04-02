package ViewController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;

public class MainView extends View {

    private Scene scene;
    private StackPane root;

    public MainView(ViewManager viewManager) {
        super(viewManager);

        this.root = new StackPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-color: black;");

        this.scene = new Scene(this.root);

        Button startButton = new Button();
        startButton.setText("Start game");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.GAME);
            }
        });
        this.root.getChildren().add(startButton);

        this.root.requestFocus();
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
