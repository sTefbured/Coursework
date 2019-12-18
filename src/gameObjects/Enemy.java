package gameObjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Enemy extends Sprite {
    private static final int WIDTH = 60, HEIGHT = 3 * Block.HEIGHT;
    private final int VISIBILITY_RANGE = 1500;
    private static final int MAX_HEALTH = 50;

    private Player player;

    public Enemy(float x, float y, Handler handler) {
        super(x, y, MAX_HEALTH, handler);
        player = handler.getPlayer();
    }

    @Override
    public void update() {
        if (isShooting && ((System.currentTimeMillis() - timerShooting) >= 700)) {
            isShooting = false;
        }
        if (healthPoints <= 0) {
            healthPoints = 0;
            isDead = true;
        }
        super.update();
        if (seePlayer()) {
            attack();
        }
    }

    private boolean seePlayer() {
        return getVisibilityBounds().intersects(player.getBounds());
    }

    private void attack() {
        float distance = (x + WIDTH / 2.0f) - (player.getX() + player.getWidth() / 2.0f);
        if (Math.abs(distance) <= VISIBILITY_RANGE / 4.0f) {
            if (!isShooting) {
                shoot(distance);
            }
        }
    }

    private void shoot(float distance) {
        isShooting = true;
        timerShooting = System.currentTimeMillis();
        if (distance < 0) {
            handler.getBullets().add(new Bullet(x + WIDTH + 5, y + (float) WIDTH / 2,
                    20, handler));
        } else if (distance > 0) {
            handler.getBullets().add(new Bullet(x - Bullet.WIDTH - 5, y + (float) WIDTH / 2,
                    -20, handler));
        }
    }

    @Override
    protected void collision() {
        super.collision();
//        bulletCollision();
    }

//    private void bulletCollision() {
//        for (Bullet bullet : handler.getBullets()) {
//            if (getBounds().intersects(bullet.getBounds())) {
//                isDead = true;
//            }
//        }
//    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.green);
        graphics2D.draw(getVisibilityBounds());
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {

    }

    public Rectangle getVisibilityBounds() {
        return new Rectangle((int) (x + WIDTH / 2) - VISIBILITY_RANGE / 2, (int) y, VISIBILITY_RANGE, HEIGHT);
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
