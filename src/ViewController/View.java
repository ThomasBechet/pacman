package ViewController;

import javafx.scene.Scene;
import javafx.stage.WindowEvent;

public abstract class View {
    protected ViewManager viewManager;

    public View(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public abstract void initialize();
    public abstract void terminate();
    public abstract Scene getScene();
    public void onSizeChanged(int width, int height) {}
    public void onWindowEvent(WindowEvent event) {}
}
