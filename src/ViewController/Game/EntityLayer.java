package ViewController.Game;

import Model.*;
import Network.Messages.EntityMessage;
import Network.Messages.GhostMessage;
import Network.Messages.MovableEntityMessage;
import Network.Messages.PacmanMessage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class EntityLayer {
    private CellLayer cellLayer;
    private StackPane pane;
    private Map<Integer, Sprite> sprites;

    public EntityLayer(CellLayer cellLayer) {
        this.cellLayer = cellLayer;
        this.sprites = new HashMap<>();

        this.pane = new StackPane();
        this.cellLayer.getChildren().add(pane);
        this.pane.toFront();
        this.pane.setAlignment(Pos.CENTER);
    }

    public void updateEntity(EntityMessage entityMessage) {
        Sprite sprite = this.sprites.get(entityMessage.id);
        if (sprite == null) {
            if (entityMessage instanceof PacmanMessage) {
                sprite = new PacmanAnimation();
                //sprite = new GhostAnimation(Ghost.BLUE);
            } else if (entityMessage instanceof GhostMessage) {
                sprite = new GhostAnimation(((GhostMessage)entityMessage).ghostId);
            }

            sprite.setTranslateX(entityMessage.position.x * Sprite.TILE_SIZE); // Initial position
            sprite.setTranslateY(entityMessage.position.y * Sprite.TILE_SIZE); // Initial position

            this.sprites.put(entityMessage.id, sprite);
            Platform.runLater(() -> {
                Sprite addedSprite = this.sprites.get(entityMessage.id);
                this.pane.getChildren().add(addedSprite);
                addedSprite.toFront();
            });
        }

        if (entityMessage instanceof PacmanMessage || entityMessage instanceof GhostMessage) {
            MovableEntityAnimation movableEntityAnimation = (MovableEntityAnimation)sprite;
            movableEntityAnimation.update((MovableEntityMessage)entityMessage);
        }
    }
}
