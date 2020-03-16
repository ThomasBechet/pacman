package Model;

public abstract class Entity {
    protected Grid grid;

    public Entity(Grid grid) {
        this.grid = grid;
    }

    public abstract void update();
}
