/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.*;

import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author frederic.armetta
 */
public class SimpleVC extends Application {
    public final int SIZE_X = 21;
    public final int SIZE_Y = 21;
    
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        // SimplePacMan spm = new SimplePacMan(SIZE_X, SIZE_Y); // initialisation du modèle
        
        GridPane grid = new GridPane(); // création de la grille
        
        // Pacman.svg.png
        Image imPM = new Image("Pacman.png"); // préparation des images
        Image imVide = new Image("Vide.png");
        Image imWall = new Image("mur.png");
        Image imDoor = new Image("Door.png");

        //img.setScaleY(0.01);
        //img.setScaleX(0.01);
        
        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement

        for (int i = 0; i < SIZE_X; i++) { // initialisation de la grille (sans image)
            for (int j = 0; j < SIZE_Y; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }
        
        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < SIZE_X; i++) { // rafraichissement graphique
                    for (int j = 0; j < SIZE_Y; j++) {
                        /* if (spm.getX() == i && spm.getY() == j) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imPM);
                        } else { */
                        /* if (game.getGrid().getCell(i, j) instanceof Wall) {
                            tab[i][j].setImage(imWall);
                        } else if (game.getGrid().getCell(i, j) instanceof Floor) {
                            tab[i][j].setImage(imVide);
                        } else if (game.getGrid().getCell(i, j) instanceof Door) {
                            tab[i][j].setImage(imDoor);
                        } */
                        // }
                    }
                }    
            }
        };

        // game.addObserver(o);
        // game.start(); // on démarre spm
        
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(grid);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.isShiftDown()) {
                    // spm.initXY(); // si on clique sur shift, on remet spm en haut à gauche
                }
            }
        });
        
        grid.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
