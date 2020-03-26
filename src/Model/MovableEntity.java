package Model;

import java.awt.*;

public class MovableEntity extends Entity {
    private boolean isMoving;
    private Direction direction;
    private int speed;
    public enum EntityState {
        DEAD,
        ALIVE,
        PENDING
    }
    private EntityState entityState;

    public MovableEntity(Grid grid) {
        super(grid);
        this.isMoving = false;
        this.direction = Direction.LEFT;
        this.speed = 300;
        this.entityState = EntityState.PENDING;
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

    public Point getSpawn() {
        return this.grid.getEntitySpawn(this);
    }

    public void tick() {  }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public EntityState getEntityState() {
        return entityState;
    }
}
