package ViewController.Game;

import Model.Direction;
import Model.MovableEntity;
import ViewController.Game.AnimationImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;

public class MovableEntityAnimation extends AnimationImage {
    private Timeline timeline;
    private Point start;
    private Point stop;
    private int duration;

    private class Frame implements EventHandler<ActionEvent> {
        private ImageView image;
        private Point position;

        public Frame(ImageView image, Point position) {
            this.image = image;
            this.position = position;
        }

        @Override
        public void handle(ActionEvent event) {
            Platform.runLater(() -> {
                this.image.setTranslateX(this.position.x);
                this.image.setTranslateY(this.position.y);
            });
        }
    }

    public MovableEntityAnimation(Image image) {
        super(image);

        this.timeline = new Timeline();
        this.timeline.setCycleCount(1);
        this.timeline.setAutoReverse(false);
    }

    public void update(MovableEntity entity, Point position) {

        synchronized (this.timeline) {
            this.duration = entity.getSpeed();
            Direction direction = entity.getDirection();
            if (entity.isMoving()) {
                this.start = new Point(position.x * 20, position.y * 20);
                if (direction == Direction.RIGHT) {
                    this.stop = new Point(this.start.x + 20, this.start.y);
                } else if (direction == Direction.DOWN) {
                    this.stop = new Point(this.start.x, this.start.y + 20);
                } else if (direction == Direction.LEFT) {
                    this.stop = new Point(this.start.x - 20, this.start.y);
                } else if (direction == Direction.UP) {
                    this.stop = new Point(this.start.x, this.start.y - 20);
                }

                this.playAnimation();
            }
        }

    }

    private void playAnimation() {

        Platform.runLater(() -> {
            synchronized (this.timeline) {
                if (this.timeline != null) {
                    this.timeline.stop();
                }

                int subdivision = this.duration / 10;

                this.timeline.getKeyFrames().clear();
                for (int i = 0; i < subdivision; i++) {
                    double t = (double)i / (double)(subdivision - 1);
                    int posX = start.x + (int)(t * (double)(stop.x - start.x));
                    int posY = start.y + (int)(t * (double)(stop.y - start.y));
                    int time = (int)(t * (double)duration);
                    this.timeline.getKeyFrames().add(new KeyFrame(new Duration(time), new Frame(this, new Point(posX, posY))));
                }
                this.timeline.play();
            }

        });

    }
}
