package Network.Messages;

import Model.Direction;
import Model.MovableEntity;
import Model.Pacman;
import Network.Parameter;

import java.awt.*;

public class PacmanMessage extends MovableEntityMessage {
    public int lifes;
    public int score;
    public boolean hero;

    public PacmanMessage(Pacman pacman, Point position, Integer id) {
        super(pacman, position, id);
    }

    public PacmanMessage(Parameter[] parameters) {
        super(parameters);

        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "lifes":
                    this.lifes = Integer.parseInt(parameter.value);
                    break;
                case "score":
                    this.score = Integer.parseInt(parameter.value);
                    break;
                case "hero":
                    this.hero = parameter.value.equals("true");
                    break;
            }
        }
    }

    @Override
    public final String toString() {
        String s = "";
        s += ";lifes=" + this.lifes;
        s += ";score=" + this.score;
        if (this.hero) {
            s += ";hero=true";
        } else {
            s += ";hero=false";
        }

        return "pacman@" + super.toString() + s;
    }
}
