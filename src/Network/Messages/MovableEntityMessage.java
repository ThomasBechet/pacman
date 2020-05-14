package Network.Messages;

import Model.Direction;
import Model.MovableEntity;
import Network.Parameter;

import java.awt.*;

public class MovableEntityMessage extends EntityMessage {
    public Direction direction = Direction.LEFT;
    public boolean isMoving = false;
    public int speed = 0;
    public MovableEntity.EntityState entityState = MovableEntity.EntityState.ALIVE;
    public Point spawn = new Point();

    public MovableEntityMessage(MovableEntity entity, Point position, Integer id) {
        super(entity, position, id);
        this.direction = entity.getDirection();
        this.isMoving = entity.isMoving();
        this.speed = entity.getSpeed();
        this.entityState = entity.getEntityState();
        this.spawn = entity.getSpawn();
    }

    public MovableEntityMessage(Parameter[] parameters) {
        super(parameters);

        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "direction":
                    switch(parameter.value) {
                        case "up":
                            this.direction = Direction.UP;
                            break;
                        case "down":
                            this.direction = Direction.DOWN;
                            break;
                        case "left":
                            this.direction = Direction.LEFT;
                            break;
                        case "right":
                            this.direction = Direction.RIGHT;
                            break;
                    }
                    break;
                case "ismoving":
                    this.isMoving = parameter.value.equals("true");
                    break;
                case "speed":
                    this.speed = Integer.parseInt(parameter.value);
                    break;
                case "state":
                    switch (parameter.value) {
                        case "dead":
                            this.entityState = MovableEntity.EntityState.DEAD;
                            break;
                        case "alive":
                            this.entityState = MovableEntity.EntityState.ALIVE;
                            break;
                        case "pending":
                            this.entityState = MovableEntity.EntityState.PENDING;
                            break;
                    }
                    break;
                case "spawn_x":
                    this.spawn.x = Integer.parseInt(parameter.value);
                    break;
                case "spawn_y":
                    this.spawn.y = Integer.parseInt(parameter.value);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        switch (this.direction) {
            case UP:
                s += ";direction=up";
                break;
            case DOWN:
                s += ";direction=down";
                break;
            case LEFT:
                s += ";direction=left";
                break;
            case RIGHT:
                s += ";direction=right";
                break;
        }

        if (this.isMoving) {
            s += ";ismoving=true";
        } else {
            s += ";ismoving=false";
        }

        s += ";speed=" + this.speed;

        switch (this.entityState) {
            case DEAD:
                s += ";state=dead";
                break;
            case ALIVE:
                s += ";state=alive";
                break;
            case PENDING:
                s+= ";state=pending";
                break;
        }

        s += ";spawn_x=" + this.spawn.x;
        s += ";spawn_y=" + this.spawn.y;

        return super.toString() + s;
    }
}
