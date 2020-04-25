package Network.Messages;

import Model.GameState;
import Network.Parameter;

public class GameStateMessage extends Message {
    public int totalPlayer;
    public int currentPlayerCount;
    public boolean waitingConnection;

    public GameState.FlowState flowState;

    public GameStateMessage(GameState gameState) {
        this.waitingConnection = false;
        this.flowState = gameState.getFlowState();
    }
    public GameStateMessage(int currentPlayerCount, int totalPlayer) {
        this.waitingConnection = true;
        this.currentPlayerCount = currentPlayerCount;
        this.totalPlayer = totalPlayer;
    }

    public GameStateMessage(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            switch (parameter.key) {
                case "currentPlayerCount":
                    this.waitingConnection = true;
                    this.currentPlayerCount = Integer.parseInt(parameter.value);
                    break;
                case "totalPlayer":
                    this.waitingConnection = true;
                    this.totalPlayer = Integer.parseInt(parameter.value);
                    break;
                case "state":
                    this.waitingConnection = false;
                    switch (parameter.value) {
                        case "paused":
                            this.flowState = GameState.FlowState.PAUSED;
                            break;
                        case "running":
                            this.flowState = GameState.FlowState.RUNNING;
                            break;
                        case "stopped":
                            this.flowState = GameState.FlowState.STOPPED;
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        if (this.waitingConnection) {
            s += "currentPlayerCount=" + this.currentPlayerCount;
            s += ";totalPlayer=" + this.totalPlayer;
        } else {
            switch (this.flowState) {
                case PAUSED:
                    s += "state=paused";
                    break;
                case RUNNING:
                    s += "state=running";
                    break;
                case STOPPED:
                    s+= "state=stopped";
                    break;
            }
        }
        return "gamestate@" + s;
    }
}
