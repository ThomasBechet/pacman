package Model;

public class Pacman extends Entity {
    private Direction direction;
    private int lifes;
    private int score;

    public Pacman(Grid grid, int lifeCount) {
        super(grid);

        this.lifes = lifeCount;
        this.score = 0;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLifeCount() {
        return this.lifes;
    }

    public void addLife() {
        this.lifes++;
    }

    public void removeLife() {
        if (this.lifes > 0) {
            this.lifes--;
        }
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void update() {

    }
}
