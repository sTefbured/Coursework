package framework;

import main.Game;

import java.awt.*;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected Textures textures;

    protected GameObject(float x, float y) {
        this.x = x;
        this.y = y;
        textures = Game.getTextures();
    }

    public abstract void render(Graphics2D graphics2D);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, getWidth(), getHeight());
    }
}
