package gameObjects;

import framework.Sprite;
import main.Camera;
import main.Game;
import main.Handler;

import java.awt.*;
import java.io.File;
import java.util.List;

public class Player extends Sprite {
    public static final int WIDTH = 60, HEIGHT = 3 * Block.HEIGHT;
    public static final int IMG_DELTA = 10;
    private static final int MAX_HEALTH = 100;

    private Camera camera;
    private Font font;

    public Player(float x, float y, Handler handler) {
        super(x, y, MAX_HEALTH, true, handler);
        this.camera = null;
        isGettingDamage = false;
        isFacingLeft = false;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE_INV.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (isGettingDamage && (System.currentTimeMillis() - timerGettingDamage) >= 500) {
            isGettingDamage = false;
            speedX = 0;
        }
        if (isShooting && (System.currentTimeMillis() - timerShooting) >= 200) {
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

        levelEndCollision();
    }

    private void bonusCollision() {
        List<Bonus> bonuses = handler.getBonuses();
        for (int i = 0, size = bonuses.size(); i < size; i++) {
            Bonus bonus = bonuses.get(i);
            if (getBounds().intersects(bonus.getBounds())) {
                bonuses.remove(bonus);
                healthPoints += 10;
                if (healthPoints > MAX_HEALTH) {
                    healthPoints = MAX_HEALTH;
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
            if (enemy.isDead) {
                continue;
            }
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
                timerGettingDamage = System.currentTimeMillis();
            } else if (getRightBounds().intersects(enemy.getBounds())) {
                x = enemy.getX() - getWidth();
                speedX = -5;
                speedY = -10;
                healthPoints -= 15;
                isGettingDamage = true;
                timerGettingDamage = System.currentTimeMillis();
            }
        }
    }

    private void levelEndCollision() {
        for (LevelEnd levelEnd : handler.getLevelEnds()) {
            if (getBounds().intersects(levelEnd.getBounds())) {
                x = levelEnd.getX() - WIDTH;
                handler.clearLevel();
                Game.isRunning = false;
                break;
            }
        }
    }

    @Override
    public void getDamage(int damagePoints) {
        super.getDamage(damagePoints);
        isGettingDamage = true;
    }

    private void drawHealthBar(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(10));
        graphics2D.drawLine(
                -camera.getX() + 10,
                -camera.getY() + 15,
                -camera.getX() + 10 + healthPoints * 3,
                -camera.getY() + 15);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font.deriveFont(Font.PLAIN, 20));
        graphics2D.drawString(healthPoints + "%", -camera.getX() + 10 + 10 + healthPoints * 3,
                -camera.getY() + 10 + 15);
    }

    public void shoot() {
        isShooting = true;
        if (isFacingLeft) {
            handler.getBullets().add(new Bullet(x - Bullet.WIDTH - 5, y + (float) WIDTH / 2 + 10,
                    -20 + speedX, handler));
        } else {
            handler.getBullets().add(new Bullet(x + WIDTH + 5, y + (float) WIDTH / 2 + 10,
                    20 + speedX, handler));
        }
        timerShooting = System.currentTimeMillis();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
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
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getPlayerDieLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (isJumping) {
                graphics2D.drawImage(animation.getSpriteImage(0, true, textures.getPlayerJumpLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getPlayerRunLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else {
                graphics2D.drawImage(animation.getSpriteImage(30, true, textures.getPlayerIdleLeft()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }
        } else {
            if (isShooting) {
                graphics2D.drawImage(animation.getSpriteImage(5, true, textures.getPlayerAttackRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }else if (isDead) {
                graphics2D.drawImage(animation.getSpriteImage(15, false, textures.getPlayerDieRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }
            else if (isJumping) {
                graphics2D.drawImage(animation.getSpriteImage(0, true, textures.getPlayerJumpRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else if (speedX != 0) {
                graphics2D.drawImage(animation.getSpriteImage(15, true, textures.getPlayerRunRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            } else {
                graphics2D.drawImage(animation.getSpriteImage(30, true, textures.getPlayerIdleRight()),
                        (int) x - IMG_DELTA, (int) y, WIDTH + 2 * IMG_DELTA, HEIGHT, null);
            }
        }
    }

    public void setAlive() {
        isDead = false;
        healthPoints = MAX_HEALTH;
    }
}