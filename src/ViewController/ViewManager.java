package ViewController;

import ViewController.Game.GameView;
import ViewController.Menu.JoinCreateView;
import ViewController.Menu.JoinView;
import ViewController.Menu.MainView;
import ViewController.Menu.MapSelectionView;
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
        MAP_SELECTION,
        GAME,
        JOIN_CREATE_SERVER,
        SERVER_CONNECTION
    }

    public class Parameters {
        public Integer port;
        public Inet4Address address;
        public String map;

        public void reset() {
            this.port = null;
            this.address = null;
            this.map = null;
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
        } else if (state == State.MAP_SELECTION) {
            this.currentView = new MapSelectionView(this);
        } else if (state == State.GAME) {
            this.currentView = new GameView(this);
        } else if (state == State.JOIN_CREATE_SERVER) {
            this.currentView = new JoinCreateView(this);
        } else if (state == State.SERVER_CONNECTION) {
            this.currentView = new JoinView(this);
        }

        this.stage.setScene(this.currentView.getScene());
        this.changed(null, null, null); // Update size window
    }

    public void terminate() {
        this.stage.close();
    }

    public Parameters getParameters() {
        return this.parameters;
    }
}
