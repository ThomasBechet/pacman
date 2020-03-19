package Model;

public class PacmanController {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int PLAYER_3 = 2;
    public static final int PLAYER_4 = 3;

    private Pacman pacman;

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public void up() {
        if (this.pacman != null) {
            this.pacman.setDirection(Direction.UP);
        }
    }

    public void down() {
        if (this.pacman != null) {
            this.pacman.setDirection(Direction.DOWN);
        }
    }

    public void left() {
        if (this.pacman != null) {
            this.pacman.setDirection(Direction.LEFT);
        }
    }

    public void right() {
        if (this.pacman != null) {
            this.pacman.setDirection(Direction.RIGHT);
        }
    }
}
