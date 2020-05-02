package Maps;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapTools {
    public static List<String> enumerateMaps() {
        ArrayList<String> maps = new ArrayList<>();
        File dir = new File("src/Maps");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                int i = child.getName().lastIndexOf('.');
                if (i > 0) {
                    String extension = child.getName().substring(i + 1);
                    if (extension.equals("txt")) {
                        maps.add(child.getName().replaceFirst("[.][^.]+$", ""));
                    }
                }
            }
        }

        return maps;
    }

    public static char[][] loadMap(String map) {
        int width = 0;
        int height = 0;
        String characters = new String();

        try {
            FileReader fileReader = new FileReader("src/Maps/" + map + ".txt");
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

        char array[][] = new char[width][height];
        int index = 0;
        for (char c : characters.toCharArray()) {
            array[index % width][index / width] = c;
            index++;
        }

        return array;
    }
    public static void saveMap(String name, char array[][]) {
        try {
            PrintWriter outFile = new PrintWriter(new FileWriter("src/Maps/" + name + ".txt"));
            for (int y = 0; y < array[0].length; y++) {
                for (int x = 0; x < array.length; x++) {
                    outFile.write(array[x][y]);
                }
                outFile.write("\n");
            }
            outFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
