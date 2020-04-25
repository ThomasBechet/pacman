package Model;

public class GameState {
    public enum FlowState {
        STOPPED,
        RUNNING,
        PAUSED
    }

    private FlowState flowState;

    public GameState() {
        this.flowState = FlowState.STOPPED;
    }

    public void setFlowState(FlowState state) {
        this.flowState = state;
    }
    public FlowState getFlowState() {
        return this.flowState;
    }
}
