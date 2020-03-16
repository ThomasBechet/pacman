package Model;

import java.io.BufferedReader;
import java.io.FileReader;

public class Grid {
    private Cell[][] cells;

    public Grid () {
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
                            cells[j][i] = new Floor();
                            break;
                        case 'D':
                            cells[j][i] = new Door();
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

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }
}
