package Model;

public class Door extends Cell {
    int color;

    public Door(int color) {
        this.color = color;
    }

    public boolean isOpen(int id) {
        return this.color == id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void close() {
        this.color = -1;
    }

}
