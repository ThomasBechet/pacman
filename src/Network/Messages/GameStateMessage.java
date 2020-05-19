package Network.Messages;

import Model.GameState;
import Network.Parameter;

public class GameStateMessage extends Message {
    public int totalPlayer;
    public int currentPlayerCount;
    public boolean waitingConnection;

    public GameState.FlowState flowState;
    public long countdown;
    public int winner;

    public GameStateMessage(GameState gameState) {
        this.waitingConnection = false;
        this.flowState = gameState.getFlowState();
        this.countdown = gameState.getCountdown();
        this.winner = gameState.getWinner();
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
                        case "ended":
                            this.flowState = GameState.FlowState.ENDED;
                            break;
                    }
                    break;
                case "countdown":
                    this.countdown = Long.parseLong(parameter.value);
                    break;
                case "winner":
                    this.winner = Integer.parseInt(parameter.value);
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
                case COUNTDOWN:
                    s+= "countdown=" + this.countdown;
                    break;
                case ENDED:
                    s += "state=ended";
                    s += ";winner=" + this.winner;
                    break;
            }
        }
        return "gamestate@" + s;
    }
}
