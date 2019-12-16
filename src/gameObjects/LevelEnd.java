package gameObjects;

import java.awt.*;

public class LevelEnd {
    public final int WIDTH = 32, HEIGHT = 32;
    private float x, y;

    public LevelEnd(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }
}
