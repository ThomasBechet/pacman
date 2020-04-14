package ViewController.Game;

import Model.*;
import javafx.scene.input.KeyCode;

public class GameController {
    private GameView view;
    private Game game;

    private PacmanController player1;

    public GameController(GameView view) {
        this.view = view;
        this.game = new Game();

        //this.game.setMapListener(view);
        //this.game.setCellListener(view);
        //this.game.setEntityListener(view);

        this.player1 = new PacmanController();
        PacmanController player2 = new PacmanController();
        PacmanController player3 = new PacmanController();
        PacmanController player4 = new PacmanController();

        game.loadMap("src/Maps/map2.txt");

        game.setPacmanController(player1, PacmanController.PLAYER_1);
        game.setPacmanController(player2, PacmanController.PLAYER_2);
        game.setPacmanController(player3, PacmanController.PLAYER_3);
        game.setPacmanController(player4, PacmanController.PLAYER_4);

        //view.setController(this.player1);

        game.start();
    }

    public void handle(javafx.scene.input.KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.Q || code == KeyCode.LEFT) {
            this.player1.left();
        } else if (code == KeyCode.D || code == KeyCode.RIGHT) {
            this.player1.right();
        } else if (code == KeyCode.Z || code == KeyCode.UP) {
            this.player1.up();
        } else if (code == KeyCode.S || code == KeyCode.DOWN) {
            this.player1.down();
        } else if (code == KeyCode.X) {
            this.player1.inceaseSpeed();
        } else if (code == KeyCode.W) {
            this.player1.decreaseSpeed();
        } else if (code == KeyCode.K) {
            this.player1.removeLife();
        }
    }

    public void terminate() {
        this.game.stop();
    }
}
