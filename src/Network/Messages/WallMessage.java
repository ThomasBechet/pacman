package Network.Messages;

import Model.Wall;
import Network.Parameter;

import java.awt.*;

public class WallMessage extends CellMessage {
    public WallMessage(Wall wall, Point position) {
        super(wall, position);
    }
    public WallMessage(Parameter[] parameters) {
        super(parameters);
    }

    @Override
    public final String toString() {
        return "wall@" + super.toString();
    }
}
