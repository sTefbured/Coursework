package gameObjects;

import framework.Sprite;
import main.Handler;

import java.awt.*;

public class Enemy extends Sprite {
    private static final int WIDTH = 60, HEIGHT = 3 * Block.HEIGHT;
    private final int VISIBILITY_RANGE = 1500;
    private static final int MAX_HEALTH = 50;
    private static final int IMG_DELTA = 10;

    private Player player;

    public Enemy(float x, float y, Handler handler) {
        super(x, y, MAX_HEALTH, true, handler);
        player = handler.getPlayer();
        speedX = 2;
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
        super.update();
        if (isDead) {
            return;
        }
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
        return (getVisibilityBounds().intersects(player.getBounds()) && isLookingOnPlayer() && noWallsBetweenPlayer());
    }

    private boolean isLookingOnPlayer() {
        if (isFacingLeft && ((x - player.getX()) > 0)) {
            return true;
        } else {
            return !isFacingLeft && ((x - player.getX()) < 0);
        }
    }

    private boolean noWallsBetweenPlayer() {
        for (Block block : handler.getBlocks()) {
            if (!block.getBounds().intersects(getVisibilityBounds())) {
                continue;
            }
            if (isBetweenPlayerAndEnemy(block)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBetweenPlayerAndEnemy(Block block) {
        boolean isRighter = (block.getX() > x + WIDTH) && (block.getX() > player.getX() + Player.WIDTH);
        boolean isLefter = (block.getX() < x) && (block.getX() < player.getX());
        if (isLefter || isRighter) {
            System.out.println("LEFTER: " + isLefter + " RIGHTER: " + isRighter);
            return false;
        } else {
            System.out.println("ALO");
            return true;
        }
    }

    private void attack() {
        if (!isShooting && !player.isDead) {
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
        drawAnimation(graphics2D);
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {
        if (isFacingLeft) {
            if (isShooting) {
                graphics2D.drawImage(animation.getSpriteImage(5, true, textures.getEnemyAttackLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getEnemyWalkLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getEnemyDieLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }
        } else {
            if (isShooting) {
                graphics2D.drawImage(animation.getSpriteImage(5, true, textures.getEnemyAttackRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getEnemyWalkRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getEnemyDieRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }
        }
    }

    public Rectangle getVisibilityBounds() {
        return new Rectangle((int) (x + WIDTH / 2) - VISIBILITY_RANGE / 2, (int) y + 5, VISIBILITY_RANGE, HEIGHT - 10);
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
