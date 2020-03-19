package Model;

import java.awt.*;

public class Ghost extends Entity {
    private Direction direction;
    private Color color;

    public Ghost(Grid grid, Color color) {
        super(grid);
        this.color = color;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void update() {
        // Do nothing
    }
}
