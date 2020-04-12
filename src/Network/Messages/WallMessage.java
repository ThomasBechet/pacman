package Network.Messages;

import Network.Parameter;

public class WallMessage extends CellMessage {
    public WallMessage(Parameter[] parameters) {
        super(parameters);
    }

    @Override
    public final String toString() {
        return "wall@" + super.toString();
    }
}
