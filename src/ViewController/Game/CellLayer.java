package ViewController.Game;

import Model.*;
import Network.Messages.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class CellLayer extends GridPane {
    private Sprite[][] sprites;
    private boolean[][] walls;

    private final static Image imageFloor = Sprite.resample(new Image("Assets/Floor.png"));
    private final static Image imageFloorPacgumBase = Sprite.resample(new Image("Assets/FloorPacgumBase.png"));
    private final static Image imageFloorPacgumFruit = Sprite.resample(new Image("Assets/FloorPacgumFruit.png"));
    private final static Image imageFloorPacgumSuper = Sprite.resample(new Image("Assets/FloorPacgumSuper.png"));
    private final static Image imageWall = Sprite.resample(new Image("Assets/Walls.png"));
    private final static Image imageDoor = Sprite.resample(new Image("Assets/Door.png"));

    public int getGridWidth() {
        if (this.sprites != null) {
            return this.sprites.length * Sprite.TILE_SIZE;
        } else {
            return 0;
        }
    }
    public int getGridHeight() {
        if (this.sprites != null) {
            return this.sprites[0].length * Sprite.TILE_SIZE;
        } else {
            return 0;
        }
    }

    public void updateCell(CellMessage cellMessage) {
        Sprite sprite = this.sprites[cellMessage.position.x][cellMessage.position.y];
        if (cellMessage instanceof WallMessage) {
            this.buildWall(sprite, cellMessage.position);
        } else if (cellMessage instanceof FloorMessage) {
            FloorMessage floorMessage = (FloorMessage)cellMessage;
            if (floorMessage.hasPacgum) {
                PacgumType type = floorMessage.pacgumType;
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
        } else if (cellMessage instanceof DoorMessage) {
            sprite.setSpriteSheet(imageDoor);
            if (((DoorMessage) cellMessage).color == Ghost.BLUE)
                sprite.setFrame(1, 0);
            else if (((DoorMessage) cellMessage).color == Ghost.ORANGE)
                sprite.setFrame(2,0);
            else if (((DoorMessage) cellMessage).color == Ghost.PINK)
                sprite.setFrame(3, 0);
            else if (((DoorMessage) cellMessage).color == Ghost.RED)
                sprite.setFrame(4, 0);
            else
                sprite.setFrame(0, 0);
        }
    }

    public void updateMap(MapMessage mapMessage) {
        this.sprites = new Sprite[mapMessage.width][mapMessage.height];
        this.walls = mapMessage.walls;
        for (int x = 0; x < mapMessage.width; x++) {
            for (int y = 0; y < mapMessage.height; y++) {
                this.sprites[x][y] = new Sprite();
                this.add(this.sprites[x][y], x, y);
                if (this.walls[x][y]) {
                    this.buildWall(this.sprites[x][y], new Point(x, y));
                }
                this.sprites[x][y].toBack();
            }
        }
    }

    private void buildWall(Sprite sprite, Point position) {
        sprite.setSpriteSheet(imageWall);

        boolean left = position.x <= 0 || (this.walls[position.x - 1][position.y]);
        boolean right = position.x >= this.walls.length - 1 || (this.walls[position.x + 1][position.y]);
        boolean up = position.y <= 0 || (this.walls[position.x][position.y - 1]);
        boolean down = position.y >= this.walls[0].length - 1 || (this.walls[position.x][position.y + 1]);

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
