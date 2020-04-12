package Network.Messages;

import Network.Parameter;

public class DoorMessage extends CellMessage {
    public DoorMessage(Parameter[] parameters) {
        super(parameters);
    }

    @Override
    public final String toString() {
        return "door@" + super.toString();
    }
}
