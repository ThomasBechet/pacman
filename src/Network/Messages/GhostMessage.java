package Network.Messages;

import Model.Ghost;
import Network.Parameter;

import java.awt.*;

public class GhostMessage extends MovableEntityMessage {

    public GhostMessage(Ghost ghost, Point position, Integer id) {
        super(ghost, position, id);
    }

    public GhostMessage(Parameter[] parameters) {
        super(parameters);
    }

    @Override
    public final String toString() {
        return "ghost@" + super.toString();
    }
}
