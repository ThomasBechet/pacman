package Network.Messages;

import Model.Door;
import Network.Parameter;

import java.awt.*;

public class DoorMessage extends CellMessage {
    public int color;

    public DoorMessage(Door door, Point position) {
        super(door, position);
        this.color = door.getColor();
    }
    public DoorMessage(Parameter[] parameters) {
        super(parameters);
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "color":
                    this.color = Integer.parseInt(parameter.value);
                    break;
            }
        }
    }

    @Override
    public final String toString() {
        return "door@" + super.toString() + ";color=" + this.color;
    }
}
