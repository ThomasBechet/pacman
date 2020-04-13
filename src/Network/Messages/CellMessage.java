package Network.Messages;

import Model.Cell;
import Network.Parameter;

import java.awt.*;

public class CellMessage extends Message {
    public Point position = new Point();

    public CellMessage(Cell cell, Point position) {
        this.position = position;
    }

    public CellMessage(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
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
        return "x=" + this.position.x + ";y=" + this.position.y;
    }
}
