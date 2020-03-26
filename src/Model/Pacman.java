package Model;

public class Pacman extends MovableEntity {
    private Direction wantedDirection;
    private int lifes;
    private int score;
    private boolean hero;

    public Pacman(Grid grid, int lifeCount) {
        super(grid);

        this.wantedDirection = this.getDirection();
        this.lifes = lifeCount;
        this.score = 0;
        this.hero = false;
    }

    public void setWantedDirection(Direction direction) {
        this.wantedDirection = direction;
    }

    public int getLifeCount() {
        return this.lifes;
    }

    public void addLife() {
        this.lifes++;
    }

    public void removeLife() {
        this.die();
        if (this.lifes > 0) {
            this.lifes--;
            this.grid.notifyEntity(this);
        }
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void tick() {
        if (this.grid.canMove(this, this.wantedDirection)) {
            this.setDirection(this.wantedDirection);
        }
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public boolean isHero() {
        return hero;
    }
}
