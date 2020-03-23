package Model;

public class Door extends Cell {
    int color;

    public Door(int color) {
        this.color = color;
    }

    public boolean isOpenFor(int id) {
        return this.color == id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
