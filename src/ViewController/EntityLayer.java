package ViewController;

import Model.*;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EntityLayer implements EntityListener {
    private CellLayer cellLayer;
    private Map<Entity, ImageView> images;

    public EntityLayer(CellLayer cellLayer) {
        this.cellLayer = cellLayer;
        this.images = new HashMap<>();
    }

    @Override
    public void entityUpdated(Entity entity, Point position) {
        ImageView imageView = images.get(entity);
        if (imageView == null) {
            if (entity instanceof Pacman) {
                imageView = new PacmanAnimation();
                //imageView = new GhostAnimation(Ghost.BLUE);
            } else if (entity instanceof Ghost) {
                imageView = new GhostAnimation(((Ghost)entity).getId());
            }

            imageView.setTranslateX(position.x * 20.0f); // Initial position
            imageView.setTranslateY(position.y * 20.0f); // Initial position

            this.cellLayer.getChildren().add(imageView);
            this.images.put(entity, imageView);
            imageView.toFront();
        }

        if (entity instanceof Pacman || entity instanceof Ghost) {
            MovableEntityAnimation movableEntityAnimation = (MovableEntityAnimation)imageView;
            movableEntityAnimation.update((MovableEntity)entity, position);
        }
    }
}
