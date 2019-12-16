package gameObjects;

import framework.MovingObject;
import main.Handler;

import java.awt.*;

public class Bullet extends MovingObject {
    private final int WIDTH = 10, HEIGHT = 10;
    private final int MAX_LIFETIME = 3000;

    private long startTime;

    public Bullet(float x, float y, float speedX, Handler handler) {
        super(x, y, handler);
        this.speedX = speedX;
        startTime = System.currentTimeMillis();
    }

    public void setSpeedX(float speed) {
        speedX = speed;
    }

    public void setSpeedY(float speed) {
        speedY = speed;
    }

    @Override
    public void update() {
        if ((System.currentTimeMillis() - startTime) >= MAX_LIFETIME) {
            isDead = true;
        }
        x += speedX;
        collision();
    }

    @Override
    protected void collision() {
        for (Block block : handler.getBlocks()) {
            if (getBounds().intersects(block.getBounds())) {
                isDead = true;
            }
        }

        for (MysteryBox mysteryBox : handler.getMysteryBoxes()) {
            if (getBounds().intersects(mysteryBox.getBounds())) {
                isDead = true;
            }
        }

        for (Enemy enemy : handler.getEnemies()) {
            if (getBounds().intersects(enemy.getBounds())) {
                isDead = true;
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 200, 150));
        graphics2D.drawRect((int) x, (int) y, WIDTH, HEIGHT);
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
