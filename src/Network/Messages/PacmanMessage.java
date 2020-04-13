package Network.Messages;

import Model.Pacman;
import Network.Parameter;

import java.awt.*;

public class PacmanMessage extends MovableEntityMessage {
    public int lifes;
    public int score;
    public boolean hero;
    public int timeHero;

    public PacmanMessage(Pacman pacman, Point position, Integer id) {
        super(pacman, position, id);
        this.lifes = pacman.getLifeCount();
        this.score = pacman.getScore();
        this.hero = pacman.isHero();
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
                case "timeHero":
                    this.timeHero = Integer.parseInt(parameter.value);
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
        s += ";timeHero=" + this.timeHero;

        return "pacman@" + super.toString() + s;
    }
}
