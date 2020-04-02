package ViewController;

import Model.Direction;
import Model.Ghost;
import Model.MovableEntity;
import javafx.scene.image.Image;

import java.awt.*;

public class GhostAnimation extends MovableEntityAnimation {
    private final static Image imageBlueGhost = new Image("Assets/BlueGhost.png");
    private final static Image imageOrangeGhost = new Image("Assets/OrangeGhost.png");
    private final static Image imagePinkGhost = new Image("Assets/PinkGhost.png");
    private final static Image imageRedGhost = new Image("Assets/RedGhost.png");

    private static Image imageFromId(int id) {
        Image image = null;
        if (id == Ghost.BLUE) {
            image = imageBlueGhost;
        } else if (id == Ghost.ORANGE) {
            image = imageOrangeGhost;
        } else if (id == Ghost.PINK) {
            image = imagePinkGhost;
        } else if (id == Ghost.RED){
            image = imageRedGhost;
        }

        return image;
    }

    public GhostAnimation(int id) {
        super(imageFromId(id));
    }

    @Override
    public void update(MovableEntity entity, Point position) {
        super.update(entity, position);

        Direction direction = entity.getDirection();
        if (((Ghost)entity).isPanic()) {
            if (((Ghost) entity).getPanicTime() < 3000 && ((Ghost) entity).getPanicTime() % 200 < 100) {
                this.setIndex(5);
            } else {
                this.setIndex(4);
            }
        } else if (direction == Direction.RIGHT) {
            this.setIndex(0);
        } else if (direction == Direction.DOWN) {
            this.setIndex(1);
        } else if (direction == Direction.LEFT) {
            this.setIndex(2);
        } else if (direction == Direction.UP) {
            this.setIndex(3);
        }
    }
}
