package ViewController;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        GameView view = new GameView(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
