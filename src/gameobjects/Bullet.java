package gameobjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Bullet extends Sprite {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final int IMG_DELTA = 17;

    private long startTime;

    public Bullet(float x, float y, float speedX, Handler handler) {
        super(x, y, 1, false, handler);
        this.speedX = speedX;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        final int MAX_LIFETIME = 3000;
        if ((System.currentTimeMillis() - startTime) >= MAX_LIFETIME) {
            setDead(true);
        }
        x += speedX;
        collision();
    }

    @Override
    protected void collision() {
        Player player = handler.getPlayer();
        if (getBounds().intersects(player.getBounds())) {
            player.getDamage(25);
            setDead(true);
        }

        for (Block block : handler.getBlocks()) {
            if (getBounds().intersects(block.getBounds())) {
                setDead(true);
            }
        }

        for (MysteryBox mysteryBox : handler.getMysteryBoxes()) {
            if (getBounds().intersects(mysteryBox.getBounds())) {
                setDead(true);
            }
        }

        for (Enemy enemy : handler.getEnemies()) {
            if (getBounds().intersects(enemy.getBounds())) {
                enemy.getDamage(25);
                setDead(true);
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        drawAnimation(graphics2D);
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {
        if (speedX < 0) {
            graphics2D.drawImage(animation.getSpriteImage(10, false, textures.getBulletLeft()),
                    (int) x - 2 * IMG_DELTA - 2, (int) y - IMG_DELTA - 9, 64, 64, null);
        } else {
            graphics2D.drawImage(animation.getSpriteImage(10, false, textures.getBulletRight()),
                    (int) x - IMG_DELTA, (int) y - IMG_DELTA - 9, 64, 64, null);
        }
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
