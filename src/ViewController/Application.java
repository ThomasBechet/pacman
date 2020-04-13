package ViewController;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        ViewManager manager = new ViewManager(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
