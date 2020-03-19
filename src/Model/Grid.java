package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
        switch (direction) {
            case UP:
                position.y--;
                break;
            case DOWN:
                position.y++;
                break;
            case LEFT:
                position.x--;
                break;
            case RIGHT:
                position.x++;
                break;
            default:
                break;
        }

        if (position.x >= this.cells.length || position.x < 0 || position.y >= this.cells[0].length || position.y < 0) {
            return false;
        }

        return !(this.cellAt(position) instanceof Wall);
    }

    public void move(Entity entity, Direction direction) {
        if (this.canMove(entity, direction)) {
            Point point = positions.get(entity);
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

            this.entityListener.entityUpdated(entity, point);
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
        cells = new Cell[21][21];
        int i = 0;
        int j;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                j = 0;
                for (char ch : line.toCharArray()) {
                    System.out.print(ch);
                    if (ch == 'W') {
                        cells[j][i] = new Wall();
                    } else if (ch == 'F') {
                        cells[j][i] = new Floor(null);
                    } else if (ch == 'D') {
                        cells[j][i] = new Door(false);
                    } else if (Character.isDigit(ch)) {
                        cells[j][i] = new Floor(null);
                        Pacman pacman = new Pacman(this, 3);
                        Point position = new Point(j, i);
                        this.addEntity(pacman, position);
                        controllers.put(0, pacman);
                    } else if (ch == '.') {
                        cells[j][i] = new Floor(new Pacgum(10, PacgumType.BASE));
                    } else if (ch == 'O') {
                        cells[j][i] = new Floor(new Pacgum(50, PacgumType.SUPER));
                    } else if (ch == '-') {
                        cells[j][i] = new Floor(new Pacgum(100, PacgumType.FRUIT));
                    } else if (ch == 'B') {

                    } else if (ch == 'Y') {

                    } else if (ch == 'R') {

                    } else if (ch == 'P') {

                    }

                    j++;
                }
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                i++;
                System.out.println("  ");
            }
            String everything = sb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
