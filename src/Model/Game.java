package Model;

import java.nio.file.Path;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private Grid grid;
    private Sequencer sequencer;
    private GameState gameState = new GameState();
    private CellListener cellListener;
    private EntityListener entityListener;
    private GameStateListener gameStateListener;
    private MapListener mapListener;

    public void setCellListener(CellListener cellListener) {
        this.cellListener = cellListener;
    }
    public void setEntityListener(EntityListener entityListener) {
        this.entityListener = entityListener;
    }
    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }
    public void setMapListener(MapListener mapListener) {
        this.mapListener = mapListener;
    }

    public void loadMap(String file) {
        if (this.gameState.getFlowState() == GameState.FlowState.STOPPED) {
            this.grid = new Grid(file, this.cellListener, this.entityListener, this.mapListener);
        }
    }

    public void setPacmanController(PacmanController controller, int controllerIndex) {
        if (this.grid != null) {
            this.grid.setupPacmanController(controller, controllerIndex);
        }
    }

    public void start() {
        this.stop();
        this.sequencer = new Sequencer(this.grid);
        this.sequencer.start();
        this.gameState.setFlowState(GameState.FlowState.RUNNING);
        this.gameStateListener.gameStateUpdated(this.gameState);
    }
    public void stop() {
        if (this.gameState.getFlowState() != GameState.FlowState.STOPPED) {
            this.sequencer.stop();
            this.gameState.setFlowState(GameState.FlowState.STOPPED);
            this.gameStateListener.gameStateUpdated(this.gameState);
        }
    }

    public void pause() {
        if (this.gameState.getFlowState() == GameState.FlowState.RUNNING) {
            this.sequencer.stop();
            this.gameState.setFlowState(GameState.FlowState.PAUSED);
            this.gameStateListener.gameStateUpdated(this.gameState);
        }
    }
    public void resume() {
        if (this.gameState.getFlowState() == GameState.FlowState.PAUSED) {
            this.sequencer.start();
            this.gameState.setFlowState(GameState.FlowState.RUNNING);
            this.gameStateListener.gameStateUpdated(this.gameState);
        }
    }
}
