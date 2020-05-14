package Model;

import javafx.util.Duration;

import java.time.Instant;
import java.util.*;

public class Sequencer implements Runnable {
    private Thread thread;
    private List<Entity> entities;
    private Map<MovableEntity, Duration> movableEntities;
    private volatile boolean running;
    private Grid grid;
    private Timer doorsTimer;
    private Game game;

    public Sequencer(Grid grid, Game game) {
        this.entities = grid.getEntities();
        this.game = game;
        this.grid = grid;
        this.movableEntities = new HashMap<>();
        this.running = false;
        this.doorsTimer = new Timer();
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

        this.doorsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                grid.openNextDoor();
            }
        }, 5000, 5000);

        this.game.startCountdown();
        while(this.running) {

            Instant current = Instant.now();
            long timeElapsed = java.time.Duration.between(lastTime, current).toMillis();

            if (this.game.getCountdown() > 0) {
                this.game.setCountdown(this.game.getCountdown() - timeElapsed);
            } else {
                for (Entity entity : this.entities) {
                    if (entity instanceof MovableEntity) {

                        // Handle entity creation
                        MovableEntity movableEntity = (MovableEntity) entity;
                        Duration duration = this.movableEntities.get(movableEntity);
                        if (duration == null) {
                            this.movableEntities.put(movableEntity, new Duration(0));
                            duration = this.movableEntities.get(movableEntity);
                        }

                        // Update movable entities if needed
                        this.movableEntities.replace(movableEntity, new Duration(duration.toMillis() + timeElapsed));
                        if (duration.greaterThanOrEqualTo(new Duration(movableEntity.getSpeed()))) {
                            MovableEntity.EntityState oldEntityState = ((MovableEntity) entity).getEntityState();
                            entity.update((long) (duration.toMillis()));
                            if (((MovableEntity) entity).getEntityState().equals(MovableEntity.EntityState.ALIVE) && oldEntityState.equals(MovableEntity.EntityState.DEAD)) {
                               this.grid.respawn(entity);
                            }
                            this.movableEntities.replace(movableEntity, new Duration(0));
                        }

                    } else {
                        // Other entities are simply updated
                        entity.update(timeElapsed);
                    }
                }
            }

            try {
                Thread.sleep(5); // Required to reduce loop lag introduced by a zero deltatime
            } catch(InterruptedException exception) {  }

            lastTime = current;
        }

        this.doorsTimer.cancel();
    }
}
