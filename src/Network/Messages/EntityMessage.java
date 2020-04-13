package Network.Messages;

import Model.Entity;
import Network.Parameter;

import java.awt.*;

public class EntityMessage extends Message {
    public Point position = new Point();
    public int id;

    public EntityMessage(Entity entity, Point position, int id) {
        this.position = position;
        this.id = id;
    }

    public EntityMessage(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "id":
                    this.id = Integer.parseInt(parameter.value);
                    break;
                case "x":
                    this.position.x = Integer.parseInt(parameter.value);
                    break;
                case "y":
                    this.position.y = Integer.parseInt(parameter.value);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "id=" + this.id + ";x=" + this.position.x + ";y=" + this.position.y;
    }
}
