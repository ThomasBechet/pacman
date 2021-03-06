package Model;

public class PacmanController {
    public static final int PLAYER_NONE = -1;
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int PLAYER_3 = 2;
    public static final int PLAYER_4 = 3;

    private Pacman pacman;
    private int id;

    public Pacman getPacman() {
        return this.pacman;
    }

    public int getId() {
        return this.id;
    }

    public void setup(Pacman pacman, int id) {
        this.pacman = pacman;
        this.id = id;
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
}
