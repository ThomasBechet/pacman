package Network.Messages;

import Network.Parameter;

public class FloorMessage extends CellMessage {
    public FloorMessage(Parameter[] parameters) {
        super(parameters);
    }

    @Override
    public final String toString() {
        return "floor@" + super.toString();
    }
}
