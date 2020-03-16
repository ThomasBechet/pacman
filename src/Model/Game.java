package Model;

import java.nio.file.Path;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private Grid grid;
    private GameState gameState = GameState.STOPPED;
    private CellListener cellListener;
    private EntityListener entityListener;
    private GameStateListener gameStateListener;
    private Sequencer sequencer;

    public void setCellListener(CellListener cellListener) {
        this.cellListener = cellListener;
    }

    public void setEntityListener(EntityListener entityListener) {
        this.entityListener = entityListener;
    }

    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public void loadMap(Path file) {
        this.grid = new Grid(file, this.cellListener, this.entityListener);
    }

    public void setPacmanController(PacmanController controller, int controllerIndex) {

    }

    public void start() {
        this.sequencer = new Sequencer(grid.getEntities());
        this.sequencer.start();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void stop() {

    }
}
