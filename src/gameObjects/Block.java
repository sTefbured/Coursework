package gameObjects;

import framework.GameObject;

import java.awt.*;

public class Block extends GameObject {
    public static final int HEIGHT = 32;
    public static final int WIDTH = 32;

    public Block(float x, float y) {
        super(x, y);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.PINK);
        graphics2D.drawImage(textures.getBlocks()[13], (int) x, (int) y, null);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }
}
