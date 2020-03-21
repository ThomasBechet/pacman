package Model;

public class MovableEntity extends Entity {
    private boolean isMoving;
    private Direction direction;
    private int speed;

    public MovableEntity(Grid grid) {
        super(grid);
        this.isMoving = false;
        this.direction = Direction.LEFT;
        this.speed = 300;
    }

    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void update() {
        this.grid.move(this, this.direction);

        this.tick();

        this.isMoving = this.grid.canMove(this, this.direction);
        this.grid.notifyEntity(this);
    }

    public void tick() {  }
}
