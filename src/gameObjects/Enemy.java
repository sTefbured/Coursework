package gameObjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Enemy extends Sprite {
    private final int WIDTH = 60;
    private final int HEIGHT = Block.HEIGHT;

    public Enemy(float x, float y, Handler handler) {
        super(x, y, handler);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics2D graphics2D) {

        graphics2D.setColor(Color.RED);
        graphics2D.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {

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
