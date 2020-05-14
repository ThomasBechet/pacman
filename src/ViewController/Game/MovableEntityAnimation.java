package ViewController.Game;

import Model.Direction;
import Model.MovableEntity;
import Network.Messages.MovableEntityMessage;
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

    public void update(MovableEntityMessage entityMessage) {

        synchronized (this.timeline) {
            this.duration = entityMessage.speed;
            Direction direction = entityMessage.direction;

            System.out.println(entityMessage.spawn);

            if (entityMessage.entityState == MovableEntity.EntityState.ALIVE) {
                if (entityMessage.isMoving) {
                    this.start = new Point(entityMessage.position.x * Sprite.TILE_SIZE, entityMessage.position.y * Sprite.TILE_SIZE);
                    if (direction == Direction.RIGHT) {
                        this.stop = new Point(this.start.x + Sprite.TILE_SIZE, this.start.y);
                    } else if (direction == Direction.DOWN) {
                        this.stop = new Point(this.start.x, this.start.y + Sprite.TILE_SIZE);
                    } else if (direction == Direction.LEFT) {
                        this.stop = new Point(this.start.x - Sprite.TILE_SIZE, this.start.y);
                    } else if (direction == Direction.UP) {
                        this.stop = new Point(this.start.x, this.start.y - Sprite.TILE_SIZE);
                    }

                    this.playMovableAnimation();
                }
            } else if (entityMessage.entityState == MovableEntity.EntityState.PENDING) {
                this.start = entityMessage.position;
                this.stop = entityMessage.spawn;
                this.playRespawnAnimation();
            } else if (entityMessage.entityState == MovableEntity.EntityState.DEAD) {

            }
        }
    }

    private void playMovableAnimation() {
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

    private void playRespawnAnimation() {
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
