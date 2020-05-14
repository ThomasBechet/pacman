package ViewController.Editor;

import Maps.MapTools;
import ViewController.View;
import ViewController.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

public class EditorView extends View {
    private Scene scene;
    private FlowPane root;
    private GridPane editionPane;
    private EditorMapLayer editorMapLayer;

    public EditorView(ViewManager viewManager) {
        super(viewManager);

        this.root = new FlowPane();
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-image: url(\"Assets/background.png\"); -fx-background-size: stretch;");
        this.scene = new Scene(this.root);

        // Title
        TextField titleTextField = new TextField();
        if (viewManager.getParameters().map != null) {
            titleTextField.setText(viewManager.getParameters().map);
        } else {
            titleTextField.setText("new map");
        }
        titleTextField.setMinWidth(50.0);

        // Map layer
        if (viewManager.getParameters().map != null) {
            this.editorMapLayer = new EditorMapLayer(viewManager.getParameters().map);
        } else {
            this.editorMapLayer = new EditorMapLayer();
        }

        // Radio boxes
        ToggleGroup tg = new ToggleGroup();

        RadioButton selectFloor = new RadioButton("floor");
        selectFloor.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectFloor.setTextFill(Color.WHITE);
        selectFloor.setToggleGroup(tg);
        selectFloor.setId("floor");
        RadioButton selectWall = new RadioButton("wall");
        selectWall.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectWall.setTextFill(Color.WHITE);
        selectWall.setToggleGroup(tg);
        selectWall.setId("wall");
        RadioButton selectDoor = new RadioButton("door");
        selectDoor.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectDoor.setTextFill(Color.WHITE);
        selectDoor.setToggleGroup(tg);
        selectDoor.setId("door");
        RadioButton selectPacgumBase = new RadioButton("base pacgum");
        selectPacgumBase.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectPacgumBase.setTextFill(Color.WHITE);
        selectPacgumBase.setToggleGroup(tg);
        selectPacgumBase.setId("base_pacgum");
        RadioButton selectPacgumSuper = new RadioButton("super pacgum");
        selectPacgumSuper.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectPacgumSuper.setTextFill(Color.WHITE);
        selectPacgumSuper.setToggleGroup(tg);
        selectPacgumSuper.setId("super_pacgum");
        RadioButton selectFruit = new RadioButton("fruit");
        selectFruit.setFont(Font.font("Upheaval TT (BRK)", 12));
        selectFruit.setTextFill(Color.WHITE);
        selectFruit.setToggleGroup(tg);
        selectFruit.setId("fruit");

        RadioButton pacmanSpawn1 = new RadioButton("pacman 1");
        pacmanSpawn1.setFont(Font.font("Upheaval TT (BRK)", 12));
        pacmanSpawn1.setTextFill(Color.WHITE);
        pacmanSpawn1.setToggleGroup(tg);
        pacmanSpawn1.setId("pacman1");
        pacmanSpawn1.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        RadioButton pacmanSpawn2 = new RadioButton("pacman 2");
        pacmanSpawn2.setFont(Font.font("Upheaval TT (BRK)", 12));
        pacmanSpawn2.setTextFill(Color.WHITE);
        pacmanSpawn2.setToggleGroup(tg);
        pacmanSpawn2.setId("pacman2");
        RadioButton pacmanSpawn3 = new RadioButton("pacman 3");
        pacmanSpawn3.setFont(Font.font("Upheaval TT (BRK)", 12));
        pacmanSpawn3.setTextFill(Color.WHITE);
        pacmanSpawn3.setToggleGroup(tg);
        pacmanSpawn3.setId("pacman3");
        RadioButton pacmanSpawn4 = new RadioButton("pacman 4");
        pacmanSpawn4.setFont(Font.font("Upheaval TT (BRK)", 12));
        pacmanSpawn4.setTextFill(Color.WHITE);
        pacmanSpawn4.setToggleGroup(tg);
        pacmanSpawn4.setId("pacman4");

        RadioButton ghostRed = new RadioButton("ghost red");
        ghostRed.setFont(Font.font("Upheaval TT (BRK)", 12));
        ghostRed.setTextFill(Color.WHITE);
        ghostRed.setToggleGroup(tg);
        ghostRed.setId("ghost_red");
        ghostRed.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        RadioButton ghostOrange = new RadioButton("ghost orange");
        ghostOrange.setFont(Font.font("Upheaval TT (BRK)", 12));
        ghostOrange.setTextFill(Color.WHITE);
        ghostOrange.setToggleGroup(tg);
        ghostOrange.setId("ghost_orange");
        RadioButton ghostBlue = new RadioButton("ghost blue");
        ghostBlue.setFont(Font.font("Upheaval TT (BRK)", 12));
        ghostBlue.setTextFill(Color.WHITE);
        ghostBlue.setToggleGroup(tg);
        ghostBlue.setId("ghost_blue");
        RadioButton ghostPink = new RadioButton("ghost pink");
        ghostPink.setFont(Font.font("Upheaval TT (BRK)", 12));
        ghostPink.setTextFill(Color.WHITE);
        ghostPink.setToggleGroup(tg);
        ghostPink.setId("ghost_pink");

        // Back button
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setFont(Font.font("Upheaval TT (BRK)", 30));
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewManager.setView(ViewManager.State.MAIN);
            }
        });

        // Delete button
        Button deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setFont(Font.font("Upheaval TT (BRK)", 30));
        deleteButton.setStyle("-fx-background-color: transparent;");
        deleteButton.setTextFill(Color.WHITE);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MapTools.deleteMap(titleTextField.getText());
                viewManager.setView(ViewManager.State.EDITOR_MAP_SELECTION);
            }
        });

        // Save button
        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setFont(Font.font("Upheaval TT (BRK)", 30));
        saveButton.setStyle("-fx-background-color: transparent;");
        saveButton.setTextFill(Color.WHITE);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MapTools.saveMap(titleTextField.getText(), editorMapLayer.getArray());
                viewManager.setView(ViewManager.State.EDITOR_MAP_SELECTION);
            }
        });

        // Control panel
        FlowPane flowPane = new FlowPane();
        flowPane.setVgap(4.0);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.getChildren().add(selectFloor);
        flowPane.getChildren().add(selectWall);
        flowPane.getChildren().add(selectDoor);
        flowPane.getChildren().add(selectPacgumBase);
        flowPane.getChildren().add(selectPacgumSuper);
        flowPane.getChildren().add(selectFruit);

        flowPane.getChildren().add(pacmanSpawn1);
        flowPane.getChildren().add(pacmanSpawn2);
        flowPane.getChildren().add(pacmanSpawn3);
        flowPane.getChildren().add(pacmanSpawn4);

        flowPane.getChildren().add(ghostRed);
        flowPane.getChildren().add(ghostBlue);
        flowPane.getChildren().add(ghostOrange);
        flowPane.getChildren().add(ghostPink);

        flowPane.getChildren().add(saveButton);
        flowPane.getChildren().add(deleteButton);

        selectFloor.setSelected(true);

        // Edition panel
        this.editionPane = new GridPane();
        this.editionPane.setHgap(10.0);
        this.editionPane.setVgap(10.0);
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(titleTextField);
        pane.getChildren().add(this.editorMapLayer);
        pane.setMinHeight(300.0);
        pane.setMaxHeight(this.editorMapLayer.getGridHeight());
        flowPane.setMaxHeight(pane.getMinHeight());
        this.editionPane.add(pane, 0, 1, 2, 1);
        this.editionPane.add(flowPane, 2, 1);
        this.editionPane.add(backButton, 0, 2);
        this.root.getChildren().add(this.editionPane);

        // Controller
        EditorController controller = new EditorController(this.editorMapLayer, tg);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void terminate() {

    }
    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void onSizeChanged(int width, int height) {
        double factorY = (this.root.getHeight() - 30) / (this.editorMapLayer.getGridHeight() + 150);
        double factorX = this.root.getWidth() / this.editorMapLayer.getGridWidth();
        double factor = Math.min(2.0, Math.min(factorY, factorX));
        this.editionPane.setScaleX(factor);
        this.editionPane.setScaleY(factor);
    }

    @Override
    public void onWindowEvent(WindowEvent event) {

    }
}
