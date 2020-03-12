package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static PS ps;
    private static VT vt;
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // primaryStage.setTitle("Hello World");
        // primaryStage.setScene(new Scene(root, 300, 275));
        // primaryStage.show();

    }

    public static void main(String[] args) {
        // launch(args);
        ps = new PS();
        vt = new VT(ps);
        ps.addObserver(vt);
        (new Thread(ps)).start();
    }
}
