package Model;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Observable implements Runnable {
    private Grid grid;
    public Game () {
        grid = new Grid();
    }

    public Grid getGrid() {
        return grid;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true) { // spm descent dasn la grille à chaque pas de temps
            // int deltaX = r.nextInt(2);

            /* if (x + deltaX > 0 && x + deltaX < sizeX) {
                x += deltaX;
            }

            int deltaY = r.nextInt(2);
            if (y + deltaY > 0 && y + deltaY < sizeX) {
                y += deltaY;
            }

            //System.out.println(x + " - " + y);
            */
            setChanged();
            notifyObservers(); // notification de l'observer
            
            try {
                Thread.sleep(300); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
