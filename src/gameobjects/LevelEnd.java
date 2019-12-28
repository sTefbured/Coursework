package gameobjects;

import java.awt.*;

public class LevelEnd {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private float x;
    private float y;

    public LevelEnd(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }
}
