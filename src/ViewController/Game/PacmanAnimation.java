package ViewController.Game;

import Model.Direction;
import Model.MovableEntity;
import Network.Messages.MovableEntityMessage;
import ViewController.Game.MovableEntityAnimation;
import javafx.scene.image.Image;

import java.awt.*;

public class PacmanAnimation extends MovableEntityAnimation {
    public final static Image imagePacman = Sprite.resample(new javafx.scene.image.Image("Assets/Pacman.png"));

    public PacmanAnimation() {
        super(imagePacman);
    }

    @Override
    public void update(MovableEntityMessage entityMessage) {
        super.update(entityMessage);

        Direction direction = entityMessage.direction;
        if (entityMessage.entityState.equals(MovableEntity.EntityState.PENDING)) {
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
