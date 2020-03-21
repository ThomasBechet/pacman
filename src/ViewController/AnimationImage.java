package ViewController;

import Model.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedSprite extends ImageView {
    private Direction direction;
    private int currentFrame;
    private int length;

    public AnimatedSprite(Image image, int length) {
        super(image);
        this.direction = Direction.LEFT;
        this.currentFrame = 0;
        this.length = length;

        this.updateFrame();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(new Duration(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentFrame = (currentFrame + 1) % length;
                updateFrame();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        this.updateFrame();
    }

    private void updateFrame() {
        if (direction == Direction.RIGHT) {
            this.setViewport(new Rectangle2D(this.currentFrame * 20, 0, 20, 20));
        } else if (direction == Direction.DOWN) {
            this.setViewport(new Rectangle2D(this.currentFrame * 20, 20, 20, 20));
        } else if (direction == Direction.LEFT) {
            this.setViewport(new Rectangle2D(this.currentFrame * 20, 40, 20, 20));
        } else if (direction == Direction.UP) {
            this.setViewport(new Rectangle2D(this.currentFrame * 20, 60, 20, 20));
        }
    }
}
