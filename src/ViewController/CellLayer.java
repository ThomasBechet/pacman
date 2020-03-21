package ViewController;

import Model.*;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class CellLayer implements CellListener, MapListener {
    private ImageView[][] images;
    private Cell[][] cells;
    private GridPane grid;

    private final static Image imageFloor = new Image("Images/Floor.png");
    private final static Image imageFloorPacgumBase = new Image("Images/FloorPacgumBase.png");
    private final static Image imageFloorPacgumFruit = new Image("Images/FloorPacgumFruit.png");
    private final static Image imageFloorPacgumSuper = new Image("Images/FloorPacgumSuper.png");
    private final static Image imageWall = new Image("Images/Walls.png");
    private final static Image imageDoor = new Image("Images/Door.png");

    public CellLayer(StackPane pane) {
        this.grid = new GridPane();
        pane.getChildren().add(this.grid);
    }

    public ObservableList<Node> getChildren() {
        return this.grid.getChildren();
    }

    @Override
    public void cellUpdated(Cell cell, Point position) {
        ImageView imageView = this.images[position.x][position.y];
        if (cell instanceof Wall) {
            this.buildWall(imageView, position);
        } else if (cell instanceof Floor) {
            Floor floor = (Floor)cell;
            if (floor.hasPacgum()) {
                PacgumType type = floor.getPacgum().getType();
                if (type == PacgumType.BASE) {
                    imageView.setImage(imageFloorPacgumBase);
                } else if (type == PacgumType.FRUIT) {
                    imageView.setImage(imageFloorPacgumFruit);
                } else if (type == PacgumType.SUPER) {
                    imageView.setImage(imageFloorPacgumSuper);
                }
            } else {
                imageView.setImage(imageFloor);
            }
        } else if (cell instanceof Door) {
            imageView.setImage(imageDoor);
        }
    }

    @Override
    public void mapUpdated(Cell[][] cells) {
        this.cells = cells;
        this.images = new ImageView[cells.length][cells[0].length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                this.images[x][y] = new ImageView();
                this.cellUpdated(cells[x][y], new Point(x, y));
                this.grid.add(this.images[x][y], x, y);
                this.images[x][y].toBack();
            }
        }
    }

    private void buildWall(ImageView image, Point position) {
        image.setImage(imageWall);

        boolean left = position.x <= 0 || (this.cells[position.x - 1][position.y] instanceof Wall);
        boolean right = position.x >= this.cells.length - 1 || (this.cells[position.x + 1][position.y] instanceof Wall);
        boolean up = position.y <= 0 || (this.cells[position.x][position.y - 1] instanceof Wall);
        boolean down = position.y >= this.cells[0].length - 1 || (this.cells[position.x][position.y + 1] instanceof Wall);

        if (!left && !right && !up && !down) {
            image.setViewport(new Rectangle2D(0 * 20, 0, 20, 20));
        } else if (!left && right && !up && down) {
            image.setViewport(new Rectangle2D(1 * 20, 0, 20, 20));
        } else if (left && !right && !up && down) {
            image.setViewport(new Rectangle2D(2 * 20, 0, 20, 20));
        } else if (!left && right && up && !down) {
            image.setViewport(new Rectangle2D(3 * 20, 0, 20, 20));
        } else if (left && !right && up && !down) {
            image.setViewport(new Rectangle2D(4 * 20, 0, 20, 20));
        } else if (!left && right && !up && !down) {
            image.setViewport(new Rectangle2D(5 * 20, 0, 20, 20));
        } else if (left && !right && !up && !down) {
            image.setViewport(new Rectangle2D(6 * 20, 0, 20, 20));
        } else if (!left && !right && !up && down) {
            image.setViewport(new Rectangle2D(7 * 20, 0, 20, 20));
        } else if (!left && !right && up && !down) {
            image.setViewport(new Rectangle2D(8 * 20, 0, 20, 20));
        } else {
            image.setViewport(new Rectangle2D(9 * 20, 0, 20, 20));
        }
    }
}
