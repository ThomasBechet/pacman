package ViewController.Editor;

import Model.Grid;
import ViewController.Game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Arrays;

public class EditorMapLayer extends GridPane {
    private final static Image imageFloor = Sprite.resample(new Image("Assets/Floor.png"));
    private final static Image imageFloorPacgumBase = Sprite.resample(new Image("Assets/FloorPacgumBase.png"));
    private final static Image imageFloorPacgumFruit = Sprite.resample(new Image("Assets/FloorPacgumFruit.png"));
    private final static Image imageFloorPacgumSuper = Sprite.resample(new Image("Assets/FloorPacgumSuper.png"));
    private final static Image imageWall = Sprite.resample(new Image("Assets/Walls.png"));
    private final static Image imageDoor = Sprite.resample(new Image("Assets/Door.png"));
    private final static Image imagePacmanSpawns = Sprite.resample(new javafx.scene.image.Image("Assets/PacmanSpawns.png"));
    private final static Image imageGhostSpawns = Sprite.resample(new javafx.scene.image.Image("Assets/GhostSpawns.png"));

    private char array[][];
    private Sprite[][] sprites;
    private Point[] pacmanSpawns;

    public EditorMapLayer(int width, int height) {
        this.array = new char[width][height];
        this.pacmanSpawns = new Point[4];

        this.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderWidths.DEFAULT)));
        this.setHgap(1);
        this.setVgap(1);
        this.setStyle("-fx-background-color: grey;");

        for (char[] row : this.array)
            Arrays.fill(row, Grid.FLOOR_CHAR);

        this.sprites = new Sprite[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.sprites[x][y] = new Sprite();
                this.add(this.sprites[x][y], x, y);
                this.sprites[x][y].setSpriteSheet(imageFloor);
            }
        }
    }

    public void setCell(int x, int y, char c) {
        this.array[x][y] = c;

        for (int i = 0; i < this.pacmanSpawns.length; i++) {
            if (this.pacmanSpawns[i] != null && this.pacmanSpawns[i].x == x && this.pacmanSpawns[i].y == y) {
                this.pacmanSpawns[i] = null;
            }
        }

        switch (c) {
            case Grid.FLOOR_CHAR:
                this.sprites[x][y].setSpriteSheet(imageFloor);
                this.sprites[x][y].setFrame(0, 0);
                break;
            case Grid.WALL_CHAR:
                this.sprites[x][y].setSpriteSheet(imageWall);
                this.sprites[x][y].setFrame(0, 0);
                this.updateCellWall(x, y);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.DOOR_CHAR:
                this.sprites[x][y].setSpriteSheet(imageDoor);
                this.sprites[x][y].setFrame(0, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.SIMPLE_PACGUM_CHAR:
                this.sprites[x][y].setSpriteSheet(imageFloorPacgumBase);
                this.sprites[x][y].setFrame(0, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.SUPER_PACGUM_CHAR:
                this.sprites[x][y].setSpriteSheet(imageFloorPacgumSuper);
                this.sprites[x][y].setFrame(0, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.FRUIT_CHAR:
                this.sprites[x][y].setSpriteSheet(imageFloorPacgumFruit);
                this.sprites[x][y].setFrame(0, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.BLUE_GHOST_CHAR:
                this.sprites[x][y].setSpriteSheet(imageGhostSpawns);
                this.sprites[x][y].setFrame(2, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.RED_GHOST_CHAR:
                this.sprites[x][y].setSpriteSheet(imageGhostSpawns);
                this.sprites[x][y].setFrame(0, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.YELLOW_GHOST_CHAR:
                this.sprites[x][y].setSpriteSheet(imageGhostSpawns);
                this.sprites[x][y].setFrame(1, 0);
                this.refreshWallSiblings(x, y);
                break;
            case Grid.PINK_GHOST_CHAR:
                this.sprites[x][y].setSpriteSheet(imageGhostSpawns);
                this.sprites[x][y].setFrame(3, 0);
                this.refreshWallSiblings(x, y);
                break;
            default:
                if (Character.isDigit(c)) {
                    int index = Character.getNumericValue(c) - 1;
                    Point oldPosition = this.pacmanSpawns[index];
                    if (oldPosition != null) {
                        this.setCell(oldPosition.x, oldPosition.y, Grid.FLOOR_CHAR);
                    }
                    this.sprites[x][y].setSpriteSheet(imagePacmanSpawns);
                    this.sprites[x][y].setFrame(index, 0);
                    this.pacmanSpawns[index] = new Point(x, y);
                    this.refreshWallSiblings(x, y);
                }
                break;
        }
    }

    public char[][] getArray() {
        return this.array;
    }

    public int getGridHeight() {
        return this.sprites[0].length * Sprite.TILE_SIZE;
    }
    public int getGridWidth() {
        return this.sprites.length * Sprite.TILE_SIZE;
    }

    private boolean isWallOrDoor(int x, int y) {
        if (x < 0 || x >= this.array.length || y < 0 || y >= this.array[0].length) return true;
        char c = this.array[x][y];
        return c == Grid.WALL_CHAR || c == Grid.DOOR_CHAR;
    }

    private void refreshWallSiblings(int x, int y) {
        if (x > 0 && this.isWallOrDoor(x - 1, y)) {
            this.updateCellWall(x - 1, y);
        }
        if (x < this.array.length - 1 && this.isWallOrDoor(x + 1, y)) {
            this.updateCellWall(x + 1, y);
        }
        if (y > 0 && this.isWallOrDoor(x, y - 1)) {
            this.updateCellWall(x, y - 1);
        }
        if (y < this.array[0].length - 1 && this.isWallOrDoor(x, y + 1)) {
            this.updateCellWall(x, y + 1);
        }
    }
    private void updateCellWall(int x, int y) {
        if (this.array[x][y] != Grid.WALL_CHAR) return;

        Sprite sprite = this.sprites[x][y];
        sprite.setSpriteSheet(imageWall);

        boolean left = this.isWallOrDoor(x - 1, y);
        boolean right = this.isWallOrDoor(x + 1, y);
        boolean up = this.isWallOrDoor(x, y - 1);
        boolean down = this.isWallOrDoor(x, y + 1);

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
