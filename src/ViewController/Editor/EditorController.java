package ViewController.Editor;

import Model.Grid;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class EditorController implements EventHandler<MouseEvent> {
    private ToggleGroup group;
    private EditorMapLayer editorMapLayer;

    public EditorController(EditorMapLayer editorMap, ToggleGroup group) {
        this.editorMapLayer = editorMap;
        this.group = group;

        for (Node node : this.editorMapLayer.getChildren()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    }

    @Override
    public void handle(MouseEvent e) {
        Node source = (Node)e.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        RadioButton button = (RadioButton)this.group.getSelectedToggle();
        String id = button.getId();
        char c = Grid.FLOOR_CHAR;
        if (id.equals("floor")) {
            c = Grid.FLOOR_CHAR;
        } else if (id.equals("wall")) {
            c = Grid.WALL_CHAR;
        } else if (id.equals("door")) {
            c = Grid.DOOR_CHAR;
        } else if (id.equals("base_pacgum")) {
            c = Grid.SIMPLE_PACGUM_CHAR;
        } else if (id.equals("super_pacgum")) {
            c = Grid.SUPER_PACGUM_CHAR;
        } else if (id.equals("fruit")) {
            c = Grid.FRUIT_CHAR;
        } else if (id.equals("pacman1")) {
            c = '1';
        } else if (id.equals("pacman2")) {
            c = '2';
        } else if (id.equals("pacman3")) {
            c = '3';
        } else if (id.equals("pacman4")) {
            c = '4';
        } else if (id.equals("ghost_red")) {
            c = Grid.RED_GHOST_CHAR;
        } else if (id.equals("ghost_blue")) {
            c = Grid.BLUE_GHOST_CHAR;
        } else if (id.equals("ghost_orange")) {
            c = Grid.YELLOW_GHOST_CHAR;
        } else if (id.equals("ghost_pink")) {
            c = Grid.PINK_GHOST_CHAR;
        }

        this.editorMapLayer.setCell(colIndex.intValue(), rowIndex.intValue(), c);
    }
}
