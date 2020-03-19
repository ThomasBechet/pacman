package Model;

public class Ghost extends Entity {
    private Direction direction;

    public Ghost(Grid grid) {
        super(grid);
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void update() {
        // Do nothing
    }
}
