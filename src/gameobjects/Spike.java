package gameobjects;

import java.awt.*;

public class Spike extends Block {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 16;

    public Spike(float x, float y) {
        super(x, y, ObjectColor.SPIKE);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(textures.getSpikes(), (int) x, (int) y, WIDTH, HEIGHT, null);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
