package Model;

import java.awt.*;

public class Ghost extends MovableEntity {
    public final static int BLUE = 0;
    public final static int ORANGE = 1;
    public final static int PINK = 2;
    public final static int RED = 3;

    private int id;

    public Ghost(Grid grid, int id) {
        super(grid);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void tick() {
        // Do nothing
    }
}
