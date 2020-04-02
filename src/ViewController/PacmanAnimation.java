package ViewController;

import Model.Direction;
import Model.MovableEntity;
import javafx.scene.image.Image;

import java.awt.*;

public class PacmanAnimation extends MovableEntityAnimation {
    public final static Image imagePacman = new javafx.scene.image.Image("Assets/Pacman.png");

    public PacmanAnimation() {
        super(imagePacman);
    }

    @Override
    public void update(MovableEntity entity, Point position) {
        super.update(entity, position);

        Direction direction = entity.getDirection();
        if (entity.getEntityState().equals(MovableEntity.EntityState.DEAD)) {
            this.setIndex(4);
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
