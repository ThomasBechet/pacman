package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private Cell[][] cells;
    private List<Entity> entities;
    private Map<Entity, Point> positions;
    private Map<Integer, Pacman> controllers;
    private CellListener cellListener;
    private EntityListener entityListener;

    public Grid(String file, CellListener cellListener, EntityListener entityListener, MapListener mapListener) {
        this.cellListener = cellListener;
        this.entityListener = entityListener;
        this.entities = new ArrayList<Entity>();
        this.positions = new HashMap<Entity, Point>();
        this.controllers = new HashMap<Integer, Pacman>();

        this.loadMap(file);

        mapListener.mapUpdated(this.cells);
    }

    public void setupPacmanController(PacmanController controller, int index) {
        controller.setPacman(this.controllers.get(index));
    }

    public boolean canMove(Entity entity, Direction direction) {
        Point position = this.positions.get(entity);
        position = (Point)position.clone();
        this.applyDirection(position, direction);
        return this.isWalkable(this.cells[position.x][position.y]);
    }

    public void move(Entity entity, Direction direction) {
        Point point = positions.get(entity);
        if (this.canMove(entity, direction)) {
            this.applyDirection(point, direction);
            Cell curCell = this.cellAt(point);
            if (curCell instanceof Floor && entity instanceof Pacman) {
                if (((Floor)curCell).hasPacgum()) {
                    ((Pacman)entity).addScore(((Floor)curCell).getPacgum().getValue());
                    ((Floor)curCell).removePacgum();
                    this.cellListener.cellUpdated(curCell, point);
                }
            }
        }
    }

    public Cell cellAt(Point position) {
        return cells[position.x][position.y];
    }

    public Point getPacmanPosition(int controller) {
        return positions.get(controllers.get(controller));
    }

    public Pacman getPacmanByController(int controller) {
        return controllers.get(controller);
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    private Entity[] entitiesAt(Point position) {
        return null;
    }

    private void loadMap(String file) {
        int width = 0;
        int height = 0;
        String characters = new String();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                y++;
                line = line.trim();
                if (y == 1) width = line.length();
                characters += line;
            }
            height = y;
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }

        this.cells = new Cell[width][height];
        int index = 0;
        for (char c : characters.toCharArray()) {
            this.loadCellFromCharacter(c, new Point(index % width, index / width));
            index++;
        }
    }

    public void notifyEntity(Entity entity) {
        this.entityListener.entityUpdated(entity, this.positions.get(entity));
    }

    private void addEntity(Entity entity, Point point) {
        this.entities.add(entity);
        this.positions.put(entity, point);
        this.entityListener.entityUpdated(entity, point);
    }
    private void removeEntity(Entity entity) {
        this.entities.remove(entity);
        this.positions.remove(entity);
        this.entityListener.entityUpdated(entity, null);
        if (this.controllers.containsValue(entity)) {
            this.controllers.remove(this.controllers.get(entity));
        }
    }

    private void applyDirection(Point point, Direction direction) {
        switch (direction) {
            case UP:
                point.y--;
                break;
            case DOWN:
                point.y++;
                break;
            case LEFT:
                point.x--;
                break;
            case RIGHT:
                point.x++;
                break;
            default:
                break;
        }

        if (point.x >= this.cells.length) {
            point.x = 0;
        } else if (point.x < 0) {
            point.x = this.cells.length - 1;
        } else if (point.y >= this.cells[0].length) {
            point.y = 0;
        } else if (point.y < 0) {
            point.y = this.cells[0].length - 1;
        }
    }
    private boolean isWalkable(Cell cell) {
        return !(cell instanceof Wall || cell instanceof Door);
    }

    private void loadCellFromCharacter(char c, Point position) {
        if (c == 'W') {
            this.cells[position.x][position.y] = new Wall();
        } else if (c == 'F') {
            this.cells[position.x][position.y] = new Floor(null);
        } else if (c == 'D') {
            this.cells[position.x][position.y] = new Door(false);
        } else if (Character.isDigit(c)) {
            this.cells[position.x][position.y] = new Floor(null);
            Pacman pacman = new Pacman(this, 3);
            this.addEntity(pacman, position);
            this.controllers.put(0, pacman);
            int controllerIndex = Character.getNumericValue(c);
            if (controllerIndex == 1) {
                pacman.setSpeed(200);
            } else if (controllerIndex == 2) {
                pacman.setSpeed(200);
            } else if (controllerIndex == 3) {
                pacman.setSpeed(230);
            } else if (controllerIndex == 4) {
                pacman.setSpeed(100);
            }
        } else if (c == '.') {
            this.cells[position.x][position.y] = new Floor(new Pacgum(10, PacgumType.BASE));
        } else if (c == 'O') {
            this.cells[position.x][position.y] = new Floor(new Pacgum(50, PacgumType.SUPER));
        } else if (c == '-') {
            this.cells[position.x][position.y] = new Floor(new Pacgum(100, PacgumType.FRUIT));
        } else if (c == 'B') {
            this.cells[position.x][position.y] = new Floor(null);
            Ghost ghost = new Ghost(this, Ghost.BLUE);
            ghost.setDirection(Direction.UP);
            this.addEntity(ghost, position);
        } else if (c == 'Y') {
            this.cells[position.x][position.y] = new Floor(null);
            Ghost ghost = new Ghost(this, Ghost.ORANGE);
            ghost.setDirection(Direction.UP);
            this.addEntity(ghost, position);
        } else if (c == 'R') {
            this.cells[position.x][position.y] = new Floor(null);
            Ghost ghost = new Ghost(this, Ghost.RED);
            ghost.setDirection(Direction.UP);
            this.addEntity(ghost, position);
        } else if (c == 'P') {
            this.cells[position.x][position.y] = new Floor(null);
            Ghost ghost = new Ghost(this, Ghost.PINK);
            ghost.setDirection(Direction.UP);
            this.addEntity(ghost, position);
        }
    }
}
