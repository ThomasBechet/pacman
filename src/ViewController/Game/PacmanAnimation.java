package ViewController.Game;

import Model.Direction;
import Model.MovableEntity;
import Network.Messages.MovableEntityMessage;
import ViewController.Game.MovableEntityAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;

public class PacmanAnimation extends MovableEntityAnimation {
    public final static Image imagePacman = Sprite.resample(new javafx.scene.image.Image("Assets/Pacman.png"));

    public PacmanAnimation() {
        super(imagePacman);
    }

    private boolean playingPendingAnimation = false;

    @Override
    public void update(MovableEntityMessage entityMessage) {
        super.update(entityMessage);

        Direction direction = entityMessage.direction;
        if (entityMessage.entityState.equals(MovableEntity.EntityState.PENDING)) {
            if (!this.playingPendingAnimation) {
                this.playingPendingAnimation = true;
                this.setIndex(4);
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(DEATH_ANIMATION_TIME),
                        ae -> {
                            this.setIndex(5);
                        }));
                timeline.play();
            }
        } else {
            this.playingPendingAnimation = false;
            if (direction == Direction.RIGHT) {
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
}
