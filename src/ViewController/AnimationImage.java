package ViewController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;
import javafx.util.Duration;

public class AnimationImage extends Sprite {
    private int currentFrame;
    private int length;
    private int index;

    public AnimationImage(Image image) {
        super(image);
        this.currentFrame = 0;

        this.length = (int)(this.getImage().getWidth() / (Sprite.TILE_SIZE * Sprite.SCALE_FACTOR));

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(new Duration(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(() -> {
                    currentFrame = (currentFrame + 1) % length;
                    setFrame(currentFrame, index);
                });

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
    }

    public synchronized void setIndex(int index) {
        this.index = index;
        setFrame(currentFrame, index);
    }
}
