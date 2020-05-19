package Model;

public class GameState {
    public enum FlowState {
        STOPPED,
        RUNNING,
        PAUSED,
        COUNTDOWN,
        ENDED
    }

    private long countdown;
    private FlowState flowState;
    private int winner = -1;

    public GameState() {
        this.flowState = FlowState.STOPPED;
    }

    public void setFlowState(FlowState state) {
        this.flowState = state;
    }
    public FlowState getFlowState() {
        return this.flowState;
    }

    public void setCountdown (long countdown) {
        this.countdown = countdown;
        if (this.countdown <= 0) {
            this.countdown = 0;
            this.setFlowState(FlowState.RUNNING);
        }
    }
    public long getCountdown() {
        return countdown;
    }

    public void setWinner(int controllerId) {
        this.winner = controllerId;
    }
    public int getWinner() {
        return this.winner;
    }
}
