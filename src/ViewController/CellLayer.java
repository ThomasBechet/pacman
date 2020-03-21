package ViewController;

import Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class CellGrid implements CellListener, MapListener {
    private ImageView[][] images;
    private GridPane grid;

    private static Image imageFloor = new Image("Images/Floor.png");
    private static Image imageFloorPacgumBase = new Image("Images/FloorPacgumBase.png");
    private static Image imageFloorPacgumFruit = new Image("Images/FloorPacgumFruit.png");
    private static Image imageFloorPacgumSuper = new Image("Images/FloorPacgumSuper.png");
    private static Image imageWall = new Image("Images/Wall.png");
    private static Image imageDoor = new Image("Images/Door.png");

    public CellGrid(Game game, GridPane grid) {
        this.grid = grid;
    }

    @Override
    public void cellUpdated(Cell cell, Point position) {
        ImageView imageView = this.images[position.x][position.y];
        if (cell instanceof Wall) {
            imageView.setImage(imageWall);
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
        this.images = new ImageView[cells.length][cells[0].length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                this.images[x][y] = new ImageView();
                this.cellUpdated(cells[x][y], new Point(x, y));
                this.grid.add(this.images[x][y], x, y);
            }
        }
    }
}
