package ViewController;

import javafx.scene.Scene;
import javafx.stage.WindowEvent;

public abstract class View {
    public abstract void terminate();
    public abstract Scene getScene();
    public void onSizeChanged(int width, int height) {}
    public void onWindowEvent(WindowEvent event) {}
}
