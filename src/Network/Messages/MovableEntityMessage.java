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

    public MovableEntityMessage(MovableEntity entity, Point position, Integer id) {
        super(entity, position, id);
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

        return super.toString() + s;
    }
}
