package Model;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ghost extends MovableEntity {
    public final static int NONE = -1;
    public final static int BLUE = 0;
    public final static int ORANGE = 1;
    public final static int PINK = 2;
    public final static int RED = 3;
    public final static int DEAD = -2;

    private int id;
    private boolean panic;
    private int panicTime;

    public Ghost(Grid grid, int id, Point position) {
        super(grid, position);
        this.id = id;
        panic = false;
    }

    public void setPanic(boolean panic) {
        this.panic = panic;
        if (panic) {
            panicTime = 10000;

        }
    }

    public boolean isPanic() {
        return panic;
    }

    public int getPanicTime() {
        return panicTime;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void update(long timeElapsed) {
        if (panic) {
            panicTime -= timeElapsed;
            if (panicTime <= 0) {
                panic = false;
            }
        }
        super.update(timeElapsed);
    }

    @Override
    public void tick() {
        ArrayList<Direction> dir = new ArrayList<Direction>();
        int down = 0, up = 0, left = 0, right = 0;
        int baseConstante = 25;
        if (this.panic)
            baseConstante = 400;
        if (grid.canMove(this, Direction.UP)) {
            dir.add(Direction.UP);
            for (Pacman pacman : grid.getPacmans()) {
                if (grid.getEntityPosition(this).y > grid.getEntityPosition(pacman).y) {
                    up += 100;
                } else {
                    up += baseConstante;
                }
            }
            if (this.getDirection() == Direction.DOWN) {
                up = 0;
            }
        }
        if (grid.canMove(this, Direction.RIGHT)) {
            dir.add(Direction.RIGHT);
            for (Pacman pacman : grid.getPacmans()) {
                if (grid.getEntityPosition(this).x < grid.getEntityPosition(pacman).x) {
                    right += 100;
                } else {
                    right += baseConstante;
                }
            }
            if (this.getDirection() == Direction.LEFT) {
                right = 0;
            }
        }
        if (grid.canMove(this, Direction.DOWN)) {
            dir.add(Direction.DOWN);
            for (Pacman pacman : grid.getPacmans()) {
                if (grid.getEntityPosition(this).y < grid.getEntityPosition(pacman).y) {
                    down += 100;
                } else {
                    down += baseConstante;
                }
            }
            if (this.getDirection() == Direction.UP) {
                down = 0;
            }
        }
        if (grid.canMove(this, Direction.LEFT)) {
            dir.add(Direction.LEFT);
            for (Pacman pacman : grid.getPacmans()) {
                if (grid.getEntityPosition(this).x > grid.getEntityPosition(pacman).x) {
                    left += 100;
                } else {
                    left += baseConstante;
                }
            }
            if (this.getDirection() == Direction.RIGHT) {
                left = 0;
            }
        }
        if (dir.size() == 1) {
            this.setDirection(dir.get(0));
            return;
        }
        int rand = (int) (Math.random() * ( up + left + right + down - 1 ));
        rand -= up;
        if (rand < 0) {
            this.setDirection(Direction.UP);
            return;
        }
        rand -= right;
        if (rand < 0) {
            this.setDirection(Direction.RIGHT);
            return;
        }
        rand -= down;
        if (rand < 0) {
            this.setDirection(Direction.DOWN);
            return;
        }
        this.setDirection(Direction.LEFT);
    }
}