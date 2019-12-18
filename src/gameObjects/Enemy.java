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
        super(x, y, MAX_HEALTH, true, handler);
        player = handler.getPlayer();
        speedX = 3;
    }

    @Override
    public void update() {
        if (speedX > 0) {
            isFacingLeft = false;
        } else if (speedX < 0) {
            isFacingLeft = true;
        }
        if (isShooting && ((System.currentTimeMillis() - timerShooting) >= 700)) {
            isShooting = false;
        }
        if (healthPoints <= 0) {
            healthPoints = 0;
            isDead = true;
        }
        super.update();

        float distance = (x + WIDTH / 2.0f) - (player.getX() + player.getWidth() / 2.0f);
        if (seePlayer()) {
            if (Math.abs(distance) <= VISIBILITY_RANGE / 4.0f) {
                speedX = 0;
                attack();
            } else {
                chasePlayer(distance);
            }
        } else {
            search();
        }
    }

    private void chasePlayer(float distance) {
        if (distance < 0) {
            speedX = 3;
        } else {
            speedX = -3;
        }
    }

    private boolean seePlayer() {
        return (getVisibilityBounds().intersects(player.getBounds()) && (isLookingOnPlayer()));
    }

    private boolean isLookingOnPlayer() {
        if (isFacingLeft && ((x - player.getX()) > 0)) {
            return true;
        } else {
            return !isFacingLeft && ((x - player.getX()) < 0);
        }
    }

    private void attack() {
        if (!isShooting) {
            shoot();
        }
    }

    private void shoot() {
        isShooting = true;
        timerShooting = System.currentTimeMillis();
        if (!isFacingLeft) {
            handler.getBullets().add(new Bullet(x + WIDTH + 5, y + (float) WIDTH / 2,
                    20, handler));
        } else {
            handler.getBullets().add(new Bullet(x - Bullet.WIDTH - 5, y + (float) WIDTH / 2,
                    -20, handler));
        }
    }

    private void search() {
        if (speedX == 0) {
            if (isFacingLeft) {
                speedX = -3;
            } else {
                speedX = 3;
            }
        }
    }

    @Override
    protected void collision() {
        super.collision();
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
