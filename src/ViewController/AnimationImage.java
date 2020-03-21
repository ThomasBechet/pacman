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

public class AnimationImage extends ImageView {
    private int currentFrame;
    private int length;
    private int index;

    public AnimationImage(Image image) {
        super(image);
        this.currentFrame = 0;
        this.length = (int)image.getWidth() / 20;
        this.index = 0;

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
        timeline.setAutoReverse(false);
        timeline.play();
    }

    public void setIndex(int index) {
        this.index = index;
        this.updateFrame();
    }

    private void updateFrame() {
        this.setViewport(new Rectangle2D(this.currentFrame * 20, this.index * 20, 20, 20));
    }
}
