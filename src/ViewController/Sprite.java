package ViewController;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;

public class Sprite extends ImageView {
    public final static int SCALE_FACTOR = 2;
    public final static int TILE_SIZE = 20;

    private final static Image resample(Image input) {
        final int W = (int)input.getWidth();
        final int H = (int)input.getHeight();
        final int S = SCALE_FACTOR;

        WritableImage output = new WritableImage(W * S, H * S);

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb(x * S + dx, y * S + dy, argb);
                    }
                }
            }
        }

        return output;
    }

    public Sprite() {
        super();

        this.setFitHeight(TILE_SIZE);
        this.setFitWidth(TILE_SIZE);
    }
    public Sprite(Image spriteSheet) {
        super(resample(spriteSheet));

        this.setFitHeight(TILE_SIZE);
        this.setFitWidth(TILE_SIZE);
    }

    public synchronized void setFrame(int x, int y) {
        Platform.runLater(() -> {
            this.setViewport(new Rectangle2D(x * TILE_SIZE * SCALE_FACTOR,
                    y * TILE_SIZE * SCALE_FACTOR, TILE_SIZE * SCALE_FACTOR, TILE_SIZE * SCALE_FACTOR));
        });
    }

    public void setSpriteSheet(Image spriteSheet) {
        Platform.runLater(() -> {
            this.setImage(resample(spriteSheet));
        });
    }
}
