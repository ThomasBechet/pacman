package sample;

import java.util.Observable;
import java.util.Observer;

public class VT implements Observer {
    private PS ps;

    public VT (PS ps) {
        this.ps = ps;
    }
    public void update () {

    }

    @Override
    public void update(Observable o, Object arg) {
        int [][] grille = ps.getGrille();
        for (int i = 0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                System.out.print(grille[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }
}
