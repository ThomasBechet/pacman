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
    public final static double DEATH_ANIMATION_TIME = MovableEntity.RESPAWN_TIME * 0.1;

    private Timeline timeline;
    private Point start;
    private Point stop;
    private int duration;
    private boolean playingRespawnAnimation = false;

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
            if (entityMessage.entityState == MovableEntity.EntityState.ALIVE) {
                this.duration = entityMessage.speed;
                Direction direction = entityMessage.direction;

                this.playingRespawnAnimation = false;

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
                if (!this.playingRespawnAnimation) {
                    this.duration = MovableEntity.RESPAWN_TIME;
                    this.start = new Point(entityMessage.position.x * Sprite.TILE_SIZE, entityMessage.position.y * Sprite.TILE_SIZE);
                    this.stop = new Point(entityMessage.spawn.x * Sprite.TILE_SIZE, entityMessage.spawn.y * Sprite.TILE_SIZE);
                    this.playingRespawnAnimation = true;
                    this.playRespawnAnimation();
                }
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

                int stepX = Math.abs((this.stop.x / Sprite.TILE_SIZE) - (this.start.x / Sprite.TILE_SIZE));
                int stepY = Math.abs((this.stop.y / Sprite.TILE_SIZE) - (this.start.y / Sprite.TILE_SIZE));
                int durationX = stepX > 0 ? (int)(((double)stepX / (double)(stepX + stepY)) * (double)this.duration) : 0;
                int durationY = stepY > 0 ? (int)(((double)stepY / (double)(stepX + stepY)) * (double)this.duration) : 0;
                int subdivisionX = durationX / 10;
                int subdivisionY = durationY / 10;

                this.timeline.getKeyFrames().clear();

                for (int i = 0; i < subdivisionX; i++) {
                    double t = (double)i / (double)(subdivisionX - 1);
                    int posX = start.x + (int)(t * (double)(stop.x - start.x));
                    int posY = start.y;
                    int time = (int)DEATH_ANIMATION_TIME + (int)(t * (double)durationX);
                    this.timeline.getKeyFrames().add(new KeyFrame(new Duration(time), new Frame(this, new Point(posX, posY))));
                }
                for (int i = 0; i < subdivisionY; i++) {
                    double t = (double)i / (double)(subdivisionY - 1);
                    int posX = stop.x;
                    int posY = start.y + (int)(t * (double)(stop.y - start.y));
                    int time = (int)DEATH_ANIMATION_TIME + durationX + (int)(t * (double)durationY);
                    this.timeline.getKeyFrames().add(new KeyFrame(new Duration(time), new Frame(this, new Point(posX, posY))));
                }

                this.timeline.play();
            }
        });
    }
}
