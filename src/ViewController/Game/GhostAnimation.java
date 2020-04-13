package ViewController.Game;

import Model.Direction;
import Model.Ghost;
import Network.Messages.GhostMessage;
import Network.Messages.MovableEntityMessage;
import javafx.scene.image.Image;


public class GhostAnimation extends MovableEntityAnimation {
    private final static Image imageBlueGhost = Sprite.resample(new Image("Assets/BlueGhost.png"));
    private final static Image imageOrangeGhost = Sprite.resample(new Image("Assets/OrangeGhost.png"));
    private final static Image imagePinkGhost = Sprite.resample(new Image("Assets/PinkGhost.png"));
    private final static Image imageRedGhost = Sprite.resample(new Image("Assets/RedGhost.png"));

    private static Image imageFromId(int id) {
        Image image = null;
        if (id == Ghost.BLUE) {
            image = imageBlueGhost;
        } else if (id == Ghost.ORANGE) {
            image = imageOrangeGhost;
        } else if (id == Ghost.PINK) {
            image = imagePinkGhost;
        } else if (id == Ghost.RED){
            image = imageRedGhost;
        }

        return image;
    }

    public GhostAnimation(int id) {
        super(imageFromId(id));
    }

    @Override
    public void update(MovableEntityMessage entityMessage) {
        super.update(entityMessage);

        Direction direction = entityMessage.direction;
        if (((GhostMessage)entityMessage).isPanic) {
            if (((GhostMessage) entityMessage).panicTime < 3000 && ((GhostMessage)entityMessage).panicTime % 200 < 100) {
                this.setIndex(5);
            } else {
                this.setIndex(4);
            }
        } else if (direction == Direction.RIGHT) {
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
