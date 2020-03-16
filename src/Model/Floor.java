package Model;

public class Floor extends Cell {
    private Pacgum pacgum;

    public Floor(Pacgum pacgum) {
        this.pacgum = pacgum;
    }

    public boolean hasPacgum() {
        return this.pacgum != null;
    }

    public Pacgum getPacgum() {
        return this.pacgum;
    }

    public void removePacgum() {
        this.pacgum = null;
    }
}
