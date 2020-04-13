package Network.Messages;

import Model.Floor;
import Model.PacgumType;
import Network.Parameter;

import java.awt.*;

public class FloorMessage extends CellMessage {
    public boolean hasPacgum;
    public PacgumType pacgumType;

    public FloorMessage(Floor floor, Point position) {
        super(floor, position);
        this.hasPacgum = floor.hasPacgum();
        if (this.hasPacgum) {
            this.pacgumType = floor.getPacgum().getType();
        }
    }
    public FloorMessage(Parameter[] parameters) {
        super(parameters);
        this.hasPacgum = false;
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "pacgum":
                    this.hasPacgum = true;
                    switch (parameter.value) {
                        case "super":
                            this.pacgumType = PacgumType.SUPER;
                            break;
                        case "base":
                            this.pacgumType = PacgumType.BASE;
                            break;
                        case "fruit":
                            this.pacgumType = PacgumType.FRUIT;
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public final String toString() {
        String s = "";
        if (this.hasPacgum) {
            switch (this.pacgumType) {
                case SUPER:
                    s += ";pacgum=super";
                    break;
                case BASE:
                    s += ";pacgum=base";
                    break;
                case FRUIT:
                    s += ";pacgum=fruit";
                    break;
            }
        }

        return "floor@" + super.toString() + s;
    }
}
