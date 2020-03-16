package Model;

public class Pacgum {
    private int value;
    private PacgumType type;

    public Pacgum(int value, PacgumType type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return this.value;
    }
    public PacgumType getType() {
        return this.type;
    }
}
