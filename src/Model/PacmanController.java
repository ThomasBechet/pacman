package Model;

public class PacmanController {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int PLAYER_3 = 2;
    public static final int PLAYER_4 = 3;

    private Pacman pacman;
    private int index;

    public Pacman getPacman() {
        return this.pacman;
    }

    public int getIndex() {
        return this.index;
    }

    public void setup(Pacman pacman, int index) {
        this.pacman = pacman;
        this.index = index;
    }

    public void up() {
        if (this.pacman != null) {
            this.pacman.setWantedDirection(Direction.UP);
        }
    }

    public void down() {
        if (this.pacman != null) {
            this.pacman.setWantedDirection(Direction.DOWN);
        }
    }

    public void left() {
        if (this.pacman != null) {
            this.pacman.setWantedDirection(Direction.LEFT);
        }
    }

    public void right() {
        if (this.pacman != null) {
            this.pacman.setWantedDirection(Direction.RIGHT);
        }
    }

    public void inceaseSpeed() {
        if (this.pacman != null) {
            if (this.pacman.getSpeed() >= 100) this.pacman.setSpeed(this.pacman.getSpeed() - 50);
        }
    }
    public void decreaseSpeed() {
        if (this.pacman != null) {
            if (this.pacman.getSpeed() <= 500) this.pacman.setSpeed(this.pacman.getSpeed() + 50);
        }
    }
    public void removeLife() {
        if (this.pacman != null) {
            this.pacman.removeLife();
        }
    }
}
