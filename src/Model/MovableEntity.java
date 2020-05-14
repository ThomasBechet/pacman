package Model;

import java.awt.*;

public class MovableEntity extends Entity {
    public enum EntityState {
        DEAD,
        ALIVE,
        PENDING
    }

    private boolean isMoving;
    private Direction direction;
    private int speed;
    private EntityState entityState;
    private long timeBeforeRespawn;

    public MovableEntity(Grid grid) {
        super(grid);
        this.isMoving = false;
        this.direction = Direction.LEFT;
        this.speed = 300;
        this.entityState = EntityState.ALIVE;
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
    public void update(long timeElapsed) {
        timeBeforeRespawn -= timeElapsed;
        if (timeBeforeRespawn < 0 && this.entityState.equals(EntityState.PENDING)) {
            timeBeforeRespawn = 0;
            this.respawn();
        }

        this.grid.move(this, this.direction);

        this.tick();

        this.isMoving = this.grid.canMove(this, this.direction);
        this.grid.notifyEntity(this);
    }

    public Point getSpawn() {
        return (Point)(this.grid.getEntitySpawn(this).clone());
    }

    public void tick() {  }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void die() {
        this.entityState = EntityState.PENDING;
        this.timeBeforeRespawn = 3000;
    }

    public void respawn() {
        this.entityState = EntityState.ALIVE;
    }

}
