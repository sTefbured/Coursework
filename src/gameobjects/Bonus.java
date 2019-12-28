package gameobjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Bonus extends Sprite {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private long startTime;

    public Bonus(float x, float y, Handler handler) {
        super(x, y, 1, false, handler);
        this.handler = handler;
        speedX = 3;
        isFalling = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        if ((System.currentTimeMillis() - startTime) >= 10000) {
            setDead(true);
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(textures.getBonus(), (int) x, (int) y, WIDTH, HEIGHT, null);
    }

    @Override
    protected void sideBlockCollision(Block block) {
        if (getLeftBounds().intersects(block.getBounds())) {
            x = block.getX() + block.getWidth();
            speedX = 3;
        } else if (getRightBounds().intersects(block.getBounds())) {
            x = block.getX() - getWidth();
            speedX = -3;
        }
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {
        //no animation yet
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
