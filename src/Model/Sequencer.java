package Model;

import javafx.util.Duration;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sequencer implements Runnable {
    private Thread thread;
    private List<Entity> entities;
    private Map<MovableEntity, Duration> movableEntities;
    private volatile boolean running;
    private Grid grid;

    public Sequencer(List<Entity> entities, Grid grid) {
        this.entities = entities;
        this.grid = grid;
        this.movableEntities = new HashMap<>();
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
        Instant lastTime = Instant.now();
        Instant lastDoorUpdate = Instant.now();

        while(this.running) {

            Instant current = Instant.now();
            long timeElapsed = java.time.Duration.between(lastTime, current).toMillis();

            for(Entity entity : this.entities) {
                if (entity instanceof MovableEntity) {

                    // Handle entity creation
                    MovableEntity movableEntity = (MovableEntity)entity;
                    Duration duration = this.movableEntities.get(movableEntity);
                    if (duration == null) {
                        this.movableEntities.put(movableEntity, new Duration(0));
                        duration = this.movableEntities.get(movableEntity);
                    }

                    // Update movable entities if needed
                    this.movableEntities.replace(movableEntity, new Duration(duration.toMillis() + timeElapsed));
                    if (duration.greaterThanOrEqualTo(new Duration(movableEntity.getSpeed()))) {
                        entity.update();
                        this.movableEntities.replace(movableEntity, new Duration(0));
                    }

                } else {

                    // Other entities are simply updated
                    entity.update();

                }
            }

            if (java.time.Duration.between(lastDoorUpdate, current).toMillis() > 5000) {
                lastDoorUpdate = current;
                this.grid.changeDoorColor((int) (Math.random() * 5) - 1);
            }


            try {
                Thread.sleep(5); // Required to reduce loop lag introduced by a zero deltatime
            } catch(InterruptedException exception) {  }

            lastTime = current;
        }
    }
}
