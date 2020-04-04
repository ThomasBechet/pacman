package ViewController.Game;

import Model.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class CellLayer extends GridPane {
    private Sprite[][] sprites;
    private Cell[][] cells;

    private final static Image imageFloor = new Image("Assets/Floor.png");
    private final static Image imageFloorPacgumBase = new Image("Assets/FloorPacgumBase.png");
    private final static Image imageFloorPacgumFruit = new Image("Assets/FloorPacgumFruit.png");
    private final static Image imageFloorPacgumSuper = new Image("Assets/FloorPacgumSuper.png");
    private final static Image imageWall = new Image("Assets/Walls.png");
    private final static Image imageDoor = new Image("Assets/Door.png");

    public int getGridWidth() {
        return this.cells.length * Sprite.TILE_SIZE;
    }
    public int getGridHeight() {
        return this.cells[0].length * Sprite.TILE_SIZE;
    }

    public void updateCell(Cell cell, Point position) {
        Sprite sprite = this.sprites[position.x][position.y];
        if (cell instanceof Wall) {
            this.buildWall(sprite, position);
        } else if (cell instanceof Floor) {
            Floor floor = (Floor)cell;
            if (floor.hasPacgum()) {
                PacgumType type = floor.getPacgum().getType();
                if (type == PacgumType.BASE) {
                    sprite.setSpriteSheet(imageFloorPacgumBase);
                } else if (type == PacgumType.FRUIT) {
                    sprite.setSpriteSheet(imageFloorPacgumFruit);
                } else if (type == PacgumType.SUPER) {
                    sprite.setSpriteSheet(imageFloorPacgumSuper);
                }
            } else {
                sprite.setSpriteSheet(imageFloor);
            }
        } else if (cell instanceof Door) {
            sprite.setSpriteSheet(imageDoor);
            if (((Door) cell).getColor() == Ghost.BLUE)
                sprite.setFrame(1, 0);
            else if (((Door) cell).getColor() == Ghost.ORANGE)
                sprite.setFrame(2,0);
            else if (((Door) cell).getColor() == Ghost.PINK)
                sprite.setFrame(3, 0);
            else if (((Door) cell).getColor() == Ghost.RED)
                sprite.setFrame(4, 0);
            else
                sprite.setFrame(0, 0);
        }
    }

    public void updateMap(Cell[][] cells) {
        this.cells = cells;
        this.sprites = new Sprite[cells.length][cells[0].length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                this.sprites[x][y] = new Sprite();
                this.updateCell(cells[x][y], new Point(x, y));
                this.add(this.sprites[x][y], x, y);
                this.sprites[x][y].toBack();
            }
        }
    }

    private void buildWall(Sprite sprite, Point position) {
        sprite.setSpriteSheet(imageWall);

        boolean left = position.x <= 0 || (this.cells[position.x - 1][position.y] instanceof Wall);
        boolean right = position.x >= this.cells.length - 1 || (this.cells[position.x + 1][position.y] instanceof Wall);
        boolean up = position.y <= 0 || (this.cells[position.x][position.y - 1] instanceof Wall);
        boolean down = position.y >= this.cells[0].length - 1 || (this.cells[position.x][position.y + 1] instanceof Wall);

        if (!left && !right && !up && !down) {
            sprite.setFrame(0, 0);
        } else if (!left && right && !up && down) {
            sprite.setFrame(1, 0);
        } else if (left && !right && !up && down) {
            sprite.setFrame(2, 0);
        } else if (!left && right && up && !down) {
            sprite.setFrame(3, 0);
        } else if (left && !right && up && !down) {
            sprite.setFrame(4, 0);
        } else if (!left && right && !up && !down) {
            sprite.setFrame(5, 0);
        } else if (left && !right && !up && !down) {
            sprite.setFrame(6, 0);
        } else if (!left && !right && !up && down) {
            sprite.setFrame(7, 0);
        } else if (!left && !right && up && !down) {
            sprite.setFrame(8, 0);
        } else {
            sprite.setFrame(9, 0);
        }
    }
}
