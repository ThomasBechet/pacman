package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Grid {
    private Cell[][] cells;
    private Entity[] entities;
    private Map<Entity, Point> positions;
    private Map<Integer, Pacman> controllers;
    private CellListener cellListener;
    private EntityListener entityListener;

    public Grid(String file, CellListener cellListener, EntityListener entityListener) {
        this.cellListener = cellListener;
        this.entityListener = entityListener;
        this.positions = new HashMap<Entity, Point>();
        this.controllers = new HashMap<Integer, Pacman>();

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
                    switch (ch) {
                        case 'W':
                            cells[j][i] = new Wall();
                            break;
                        case 'F':
                            cells[j][i] = new Floor(new Pacgum(10, PacgumType.BASE));
                            break;
                        case 'D':
                            cells[j][i] = new Door(false);
                            break;
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

            Pacman p0 = new Pacman(this, 3);
            controllers.put(0, p0);
            positions.put(p0, new Point(10, 15));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Fin de l'init");
    }

    public boolean canMove(Entity entity, Direction direction) {
        return true;
    }

    public void move(Entity entity, Direction direction) {
        Point point = positions.get(entity);
        switch (direction) {
            case LEFT:
                point.setLocation(point.getX() - 1, point.getY());
                break;
        }
        positions.replace(entity, point);
    }

    public Cell cellAt(Point position) {
        return cells[(int) (position.getX())][(int) (position.getY())];
    }

    public Point getPacmanPosition(int controller) {
        return positions.get(controllers.get(controller));
    }

    public Pacman getPacmanByController(int controller) {
        return controllers.get(controller);
    }

    public Entity[] getEntities() {
        return this.entities;
    }

    private Entity[] entitiesAt(Point position) {
        return null;
    }
}
