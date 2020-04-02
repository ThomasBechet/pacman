package ViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ViewManager implements ChangeListener<Number>, EventHandler<WindowEvent> {
    public enum State {
        MAIN,
        GAME,
        CONNECTION
    }

    private Stage stage;
    private View currentView;

    public ViewManager(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Pacman est un très Beaujeu !");
        this.stage.setWidth(1600);
        this.stage.setHeight(900);

        this.setView(State.GAME);

        this.stage.show();

        this.stage.setOnCloseRequest(this);
        this.stage.widthProperty().addListener(this);
        this.stage.heightProperty().addListener(this);
    }

    @Override
    public void handle(WindowEvent event) {
        if (this.currentView != null) {
            this.currentView.onWindowEvent(event);

            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                this.currentView.terminate();
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (this.currentView != null) {
            this.currentView.onSizeChanged((int)this.stage.getWidth(), (int)this.stage.getHeight());
        }
    }

    public void setView(State state) {
        if (this.currentView != null) {
            this.currentView.terminate();
        }

        if (state == State.MAIN) {
            this.currentView = new MainView(this);
        } else if (state == State.GAME) {
            this.currentView = new GameView(this);
        } else if (state == State.CONNECTION) {

        }

        this.stage.setScene(this.currentView.getScene());
        this.changed(null, null, null);
    }
}
