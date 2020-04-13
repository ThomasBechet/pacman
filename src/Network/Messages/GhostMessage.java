package Network.Messages;

import Model.Ghost;
import Network.Parameter;

import java.awt.*;

public class GhostMessage extends MovableEntityMessage {
    public boolean isPanic;
    public int panicTime;
    public int ghostId;

    public GhostMessage(Ghost ghost, Point position, Integer id) {
        super(ghost, position, id);
        this.isPanic = ghost.isPanic();
        this.panicTime = ghost.getPanicTime();
        this.ghostId = ghost.getId();
    }
    public GhostMessage(Parameter[] parameters) {
        super(parameters);
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "isPanic":
                    this.isPanic = parameter.value.equals("true");
                    break;
                case "panicTime":
                    this.panicTime = Integer.parseInt(parameter.value);
                    break;
                case "ghostId":
                    this.ghostId = Integer.parseInt(parameter.value);
                    break;
            }
        }
    }

    @Override
    public final String toString() {
        String s = "";
        if (this.isPanic) {
            s += ";isPanic=true";
        } else {
            s += ";isPanic=false";
        }
        s += ";panicTime=" + this.panicTime;
        s += ";ghostId=" + this.ghostId;
        return "ghost@" + super.toString() + s;
    }
}
