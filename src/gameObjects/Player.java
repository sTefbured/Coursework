package gameObjects;

import framework.Sprite;
import main.Camera;
import main.Game;
import main.Handler;

import java.awt.*;
import java.util.List;

public class Player extends Sprite {
    public static final int WIDTH = 64, HEIGHT = 3 * Block.HEIGHT;

    private Camera camera;
    private int healthPoints;
    public boolean isGettingDamage;
    public boolean isShooting;
    public long timer;
    private boolean isFacingLeft;

    public Player(float x, float y, Handler handler, Camera camera) {
        super(x, y, handler);
        this.camera = camera;
        healthPoints = 100;
        isGettingDamage = false;
        isFacingLeft = false;
    }

    @Override
    public void update() {
        if (healthPoints <= 0) {
            healthPoints = 0;
            isDead = true;
        }
        if (isGettingDamage && (System.currentTimeMillis() - timer) >= 500) {
            isGettingDamage = false;
            speedX = 0;
        }
        if (isShooting && (System.currentTimeMillis() - timer) >= 200) {
            isShooting = false;
        }
        super.update();
        camera.setX((int) x);
        if (speedX > 0) {
            isFacingLeft = false;
        } else if (speedX < 0) {
            isFacingLeft = true;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GREEN);
        drawHealthBar(graphics2D);
        drawAnimation(graphics2D);
    }

    @Override
    protected void collision() {
        super.collision();
        bonusCollision();
        enemyCollision();
        spikeCollision();
        levelEndCollision();
    }

    private void bonusCollision() {
        List<Bonus> bonuses = handler.getBonuses();
        for (int i = 0, size = bonuses.size(); i < size; i++) {
            Bonus bonus = bonuses.get(i);
            if (getBounds().intersects(bonus.getBounds())) {
                bonuses.remove(bonus);
                healthPoints += 10;
                if (healthPoints > 100) {
                    healthPoints = 100;
                }
                size--;
                i--;
            }
        }
    }

    private void enemyCollision() {
        List<Enemy> enemies = handler.getEnemies();
        for (int i = 0, size = enemies.size(); i < size; i++) {
            Enemy enemy = enemies.get(i);

            if (getBottomBounds().intersects(enemy.getTopBounds())) {
                enemies.remove(enemy);
                i--;
                size--;
            } else if (getLeftBounds().intersects(enemy.getBounds())) {
                x = enemy.getX() + enemy.getWidth();
                speedX = 5;
                speedY = -10;
                healthPoints -= 15;
                isGettingDamage = true;
                timer = System.currentTimeMillis();
            } else if (getRightBounds().intersects(enemy.getBounds())) {
                x = enemy.getX() - getWidth();
                speedX = -5;
                speedY = -10;
                healthPoints -= 15;
                isGettingDamage = true;
                timer = System.currentTimeMillis();
            }
        }
    }

    private void spikeCollision() {
        for (Spike spike : handler.getSpikes()) {
            if (getBottomBounds().intersects(spike.getBounds())) {
                healthPoints = 0;
            }
        }
    }

    private void levelEndCollision() {
        for (LevelEnd levelEnd : handler.getLevelEnds()) {
            if (getBounds().intersects(levelEnd.getBounds())) {
                handler.clearLevel();
                break;
            }
        }
    }

    private void drawHealthBar(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawLine(
                -camera.getX() + 10,
                -camera.getY() + 15,
                -camera.getX() + 10 + healthPoints * 3,
                -camera.getY() + 15);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(Game.font.deriveFont(Font.PLAIN, 20));
        graphics2D.drawString(healthPoints + "%", -camera.getX() + 10 + 10 + healthPoints * 3,
                -camera.getY() + 10 + 15);
    }

    public void shoot() {
        isShooting = true;
        handler.getBullets().add(new Bullet(x + WIDTH, y + (float) WIDTH / 2,
                (isFacingLeft ? -20 : 20) + speedX, handler));
        timer = System.currentTimeMillis();
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    protected void drawAnimation(Graphics2D graphics2D) {
        if (isFacingLeft) {
            if (isShooting) {
                graphics2D.drawImage(animation.getSpriteImage(5, true, textures.getPlayerAttackLeft()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (isJumping) {
                graphics2D.drawImage(animation.getSpriteImage(0, true, textures.getPlayerJumpLeft()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getPlayerRunLeft()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getPlayerDieLeft()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else {
                graphics2D.drawImage(animation.getSpriteImage(30, true, textures.getPlayerIdleLeft()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            }
        } else {
            if (isShooting) {
                graphics2D.drawImage(animation.getSpriteImage(5, true, textures.getPlayerAttackRight()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (isJumping) {
                graphics2D.drawImage(animation.getSpriteImage(0, true, textures.getPlayerJumpRight()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getPlayerRunRight()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getPlayerDieRight()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            } else {
                graphics2D.drawImage(animation.getSpriteImage(30, true, textures.getPlayerIdleRight()),
                        (int) x, (int) y, WIDTH, HEIGHT, null);
            }
        }
    }
}