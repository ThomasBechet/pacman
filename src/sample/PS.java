package sample;

import java.util.Observable;
import java.util.Observer;

public class PS extends Observable implements Runnable {
    private int[][] grille;

    public PS () {
        grille = new int[10][10];
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                grille[i][j] = 0;
            }
        }
    }

    public int[][] getGrille() {
        return grille;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        }
    }
}
