package ViewController;

import Model.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EntityLayer implements EntityListener {
    private CellLayer cellLayer;
    private StackPane pane;
    private Map<Entity, Sprite> sprites;

    public EntityLayer(CellLayer cellLayer) {
        this.cellLayer = cellLayer;
        this.sprites = new HashMap<>();

        this.pane = new StackPane();
        this.cellLayer.getChildren().add(pane);
        this.pane.toFront();
        this.pane.setAlignment(Pos.CENTER);
    }

    @Override
    public void entityUpdated(Entity entity, Point position) {
        Sprite sprite = this.sprites.get(entity);
        if (sprite == null) {
            if (entity instanceof Pacman) {
                sprite = new PacmanAnimation();
                //imageView = new GhostAnimation(Ghost.BLUE);
            } else if (entity instanceof Ghost) {
                sprite = new GhostAnimation(((Ghost)entity).getId());
            }

            sprite.setTranslateX(position.x * Sprite.TILE_SIZE); // Initial position
            sprite.setTranslateY(position.y * Sprite.TILE_SIZE); // Initial position

            this.pane.getChildren().add(sprite);
            this.sprites.put(entity, sprite);
            sprite.toFront();
        }

        if (entity instanceof Pacman || entity instanceof Ghost) {
            MovableEntityAnimation movableEntityAnimation = (MovableEntityAnimation)sprite;
            movableEntityAnimation.update((MovableEntity)entity, position);
        }
    }
}
