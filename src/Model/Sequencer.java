package Model;

import java.util.List;

public class Sequencer implements Runnable {
    private Thread thread;
    private List<Entity> entities;
    private volatile boolean running;

    public Sequencer(List<Entity> entities) {
        this.entities = entities;
        this.running = false;
    }

    public void start() {
        if (!this.running) {
            this.running = true;
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void stop() {
        if (this.running) {
            this.running = false;
            try {
                this.thread.join();
            } catch(InterruptedException exception) {  }
        }
    }

    @Override
    public void run() {
        while(this.running) {
            for(Entity entity : this.entities) {
                entity.update();
            }

            try {
                Thread.sleep(300);
            } catch(InterruptedException exception) {  }
        }
    }
}
