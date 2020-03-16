package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Map;

public class Grid {
    private Cell[][] cells;
    private Entity[] entities;
    private Map<Entity, Point> positions;
    private Map<Integer, Pacman> controllers;
    private CellListener cellListener;
    private EntityListener entityListener;

    public Grid(Path file, CellListener cellListener, EntityListener entityListener) {
        this.cellListener = cellListener;
        this.entityListener = entityListener;

        cells = new Cell[21][21];
        int i = 0;
        int j;
        try(BufferedReader br = new BufferedReader(new FileReader("src/map1.txt"))) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean canMove(Entity entity, Direction direction) {
        return false;
    }

    public void move(Entity entity, Direction direction) {

    }

    public Cell cellAt(Point position) {
        return null;
    }

    public Point getPacmanPosition(int controller) {
        return null;
    }

    public Pacman getPacmanByController(int controller) {
        return null;
    }

    public Entity[] getEntities() {
        return this.entities;
    }

    private Entity[] entitiesAt(Point position) {
        return null;
    }
}
