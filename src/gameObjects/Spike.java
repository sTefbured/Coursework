package gameObjects;

import java.awt.*;

public class Spike extends Block {
    private final int WIDTH = 32;
    private final int HEIGHT = 16;

    public Spike(int x, int y) {
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
