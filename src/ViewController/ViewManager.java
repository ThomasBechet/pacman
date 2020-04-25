package ViewController;

import ViewController.Game.GameView;
import ViewController.Menu.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.Inet4Address;

public class ViewManager implements ChangeListener<Number>, EventHandler<WindowEvent> {
    static {
        Font.loadFont(ClassLoader.getSystemResource("Assets/upheavtt.ttf").toExternalForm(), 50);
        Font.loadFont(ClassLoader.getSystemResource("Assets/edunline.ttf").toExternalForm(), 150);
    }

    public enum State {
        MAIN,
        GAME,
        SOLO,
        JOIN_CREATE_SERVER,
        JOIN,
        CREATE
    }

    public class Parameters {
        public boolean solo;
        public Inet4Address address;
        public String map;
        public int playerCount;
        public int port;

        public void reset() {
            this.solo = true;
            this.address = null;
            this.map = null;
            this.playerCount = 1;
            this.port = 55555;
        }
    }

    private Stage stage;
    private View currentView;
    private Parameters parameters;

    public ViewManager(Stage stage) {
        this.parameters = new Parameters();

        this.stage = stage;
        this.stage.setTitle("Pacman est un très Beaujeu !");
        this.stage.setWidth(1600);
        this.stage.setHeight(900);

        this.setView(State.MAIN);

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
        } else if (state == State.SOLO) {
            this.currentView = new SoloView(this);
        } else if (state == State.GAME) {
            this.currentView = new GameView(this);
        } else if (state == State.JOIN_CREATE_SERVER) {
            this.currentView = new JoinCreateView(this);
        } else if (state == State.JOIN) {
            this.currentView = new JoinView(this);
        } else if (state == State.CREATE) {
            this.currentView = new CreateView(this);
        }

        this.stage.setScene(this.currentView.getScene());
        this.changed(null, null, null); // Update size window

        this.currentView.initialize();
    }

    public void terminate() {
        this.stage.close();
    }

    public Parameters getParameters() {
        return this.parameters;
    }
}
