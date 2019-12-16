package gameObjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Bonus extends Sprite {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private long startTime;

    public Bonus(float x, float y, Handler handler) {
        super(x, y, handler);
        this.handler = handler;
        speedX = 3;
        isFalling = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        if ((System.currentTimeMillis() - startTime) >= 10000) {
            isDead = true;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.CYAN);
        graphics2D.fillRect((int) x, (int) y, WIDTH, HEIGHT);

        graphics2D.setColor(Color.ORANGE);
        graphics2D.fill(getBottomBounds());
        graphics2D.fill(getTopBounds());
        graphics2D.setColor(Color.BLUE);
        graphics2D.fill(getLeftBounds());
        graphics2D.fill(getRightBounds());
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
