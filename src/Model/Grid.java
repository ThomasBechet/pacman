package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Grid {
    private Cell[][] cells;
    private List<Entity> entities;
    private Map<Entity, Point> positions;
    private Map<Entity, Point> spawns;
    private Map<Integer, Pacman> controllers;
    private CellListener cellListener;
    private EntityListener entityListener;

    public Grid(String file, CellListener cellListener, EntityListener entityListener, MapListener mapListener) {
        this.cellListener = cellListener;
        this.entityListener = entityListener;
        this.entities = new ArrayList<>();
        this.positions = new HashMap<>();
        this.spawns = new HashMap<>();
        this.controllers = new HashMap<>();

        this.loadMap(file);

        mapListener.mapUpdated(this.cells);
    }

    public void setupPacmanController(PacmanController controller, int index) {
        controller.setup(this.controllers.get(index), index);
    }

    public Collection<Pacman> getPacmans() {
        return this.controllers.values();
    }

    public boolean canMove(Entity entity, Direction direction) {
        Point position = this.positions.get(entity);
        position = (Point)position.clone();
        this.applyDirection(position, direction);
        return this.isWalkable(entity, this.cells[position.x][position.y]);
    }

    public void move(Entity entity, Direction direction) {
        Point point = positions.get(entity);
        if (this.canMove(entity, direction)) {
            this.applyDirection(point, direction);
            Cell curCell = this.cellAt(point);
            if (curCell instanceof Floor && entity instanceof Pacman) {
                if (((Floor)curCell).hasPacgum()) {
                    int oldScore = ((Pacman) entity).getScore();
                    ((Pacman)entity).addScore(((Floor)curCell).getPacgum().getValue());
                    int mod = ((Pacman) entity).getScore() / 10000;
                    if (oldScore - mod * 10000 < 0) {
                        ((Pacman) entity).addLife();
                    }
                    ((Floor)curCell).removePacgum();
                    this.cellListener.cellUpdated(curCell, point);
                }
                for (Entity e : this.getEntities()) {
                    if (e instanceof Ghost) {
                        if (getEntityPosition(entity).equals(getEntityPosition(e))) {
                            if (((Pacman) entity).isHero()) {
                                ((Ghost) e).die();
                            } else {
                                ((Pacman) entity).removeLife();
                            }
                        }
                    }
                }
            }
            if (entity instanceof Ghost) {
                for (Entity e : this.getEntities()) {
                    if (e instanceof Pacman) {
                        if (getEntityPosition(entity).equals(getEntityPosition(e))) {
                            if (((Pacman) e).isHero()) {
                                ((Ghost) entity).die();
                            } else {
                                ((Pacman) e).removeLife();
                            }
                        }
                    }
                }
            }
        }
    }

    public Cell cellAt(Point position) {
        return cells[position.x][position.y];
    }

    public Point getEntityPosition(Entity entity) {
        return positions.get(entity);
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public Point getEntitySpawn(Entity entity) {
        return spawns.get(entity);
    }

    public void openNextDoor() {
        int color = (int) (Math.random() * 5) - 1;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] instanceof Door) {
                    ((Door) cells[i][j]).setColor(color);
                    cellListener.cellUpdated(cells[i][j], new Point(i, j));
                }
            }
        }
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
        this.spawns.put(entity, point);
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
    private boolean isWalkable(Entity entity, Cell cell) {
        return !(cell instanceof Wall) && ((cell instanceof Door && entity instanceof Ghost && ((Door) cell).isOpenFor(((Ghost) entity).getId())) || !(cell instanceof Door));
    }

    private void loadCellFromCharacter(char c, Point position) {
        if (c == 'W') {
            this.cells[position.x][position.y] = new Wall();
        } else if (c == 'F') {
            this.cells[position.x][position.y] = new Floor(null);
        } else if (c == 'D') {
            this.cells[position.x][position.y] = new Door(Ghost.NONE);
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
