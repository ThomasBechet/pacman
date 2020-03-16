/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fred
 */
public class SimplePacMan extends Observable implements Runnable {
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;
    private Random r;
    
    public SimplePacMan(int sizeX, int sizeY) {
        this.r = new Random();

        // new Game();

        this.x = 0;
        this.y = 0;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    public void initXY() {
        this.x = 0;
        this.y = 0;
    }
    
    @Override
    public void run() {
        while(true) { // spm descent dasn la grille à chaque pas de temps
           int deltaX = r.nextInt(2);
           
           if (x + deltaX > 0 && x + deltaX < sizeX) {
               x += deltaX;
           }

           int deltaY = r.nextInt(2);
           if (y + deltaY > 0 && y + deltaY < sizeX) {
               y += deltaY;
           } 
           
           //System.out.println(x + " - " + y);
           
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
