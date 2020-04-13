package Network.Messages;

import Model.Cell;
import Model.Wall;
import Network.Parameter;

public class MapMessage extends Message {
    public int width;
    public int height;
    public boolean walls[][];

    public MapMessage(Cell[][] cells) {
        this.width = cells.length;
        this.height = cells[0].length;
        this.walls = new boolean[this.width][this.height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.walls[x][y] = (cells[x][y] instanceof Wall);
            }
        }
    }
    public MapMessage(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "width":
                    this.width = Integer.parseInt(parameter.value);
                    break;
                case "height":
                    this.height = Integer.parseInt(parameter.value);
                    break;
                case "walls":
                    this.walls = new boolean[this.width][this.height];
                    int i = 0;
                    for (char c : parameter.value.toCharArray()) {
                        this.walls[i % this.width][i / this.width] = (c == '1');
                        i++;
                    }
                    break;
            }
        }
    }

    @Override
    public final String toString() {
        String s = "";
        s += "width=" + this.width + ";height=" + this.height;
        s += ";walls=";
        for (int y = 0; y < this.width; y++) {
            for (int x = 0; x < this.height; x++) {
                s += this.walls[x][y] ? '1' : '0';
            }
        }
        return "map@" + s;
    }
}
