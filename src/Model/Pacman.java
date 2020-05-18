package Model;

import java.awt.*;

public class Pacman extends MovableEntity {
    private Direction wantedDirection;
    private int lifes;
    private int score;
    private boolean hero;
    private int timeHero;
    private int controller;
    private int combo;

    public Pacman(Grid grid, int lifeCount, int controller, Point position) {
        super(grid);

        this.wantedDirection = this.getDirection();
        this.lifes = lifeCount;
        this.score = 0;
        this.hero = false;
        this.controller = controller;
        this.combo = 200;
    }

    public int getControllerId() {
        return this.controller;
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
        } else {
            this.setEntityState(EntityState.DEAD);
        }
    }

    public void killGhost () {
        this.addScore(combo);
        this.combo *= 2;
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
        this.timeHero = 0;
        if (!hero) {
            combo = 200;
        }
    }

    @Override
    public void update(long timeElapsed) {
        if (this.hero) {
            this.timeHero += timeElapsed;
            if (this.timeHero >= 10000) {
                this.hero = false;
            }
        }
        super.update(timeElapsed);
    }

    public boolean isHero() {
        return this.hero;
    }
}
