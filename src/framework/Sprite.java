package framework;

import gameobjects.*;
import main.Handler;

import java.awt.*;

public abstract class Sprite extends MovingObject {
    protected static final float GRAVITY = 0.5f;

    protected int healthPoints;
    protected Animation animation;
    protected boolean isFalling;
    protected boolean isJumping;
    private boolean isShooting;
    private boolean isGettingDamage;
    protected boolean isFacingLeft;
    private long timerShooting;
    private long timerGettingDamage;
    private long deathTime;

    protected Sprite(float x, float y, int healthPoints, boolean isFacingLeft, Handler handler) {
        super(x, y, handler);
        this.healthPoints = healthPoints;
        this.isFacingLeft = isFacingLeft;
        animation = new Animation();

        speedX = speedY = 0;
        timerGettingDamage = timerShooting = System.currentTimeMillis();
        isGettingDamage = isShooting = false;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    protected void collision() {
        for (MysteryBox mysteryBox : handler.getMysteryBoxes()) {
            topBlockCollision(mysteryBox);
            bottomBlockCollision(mysteryBox);
            sideBlockCollision(mysteryBox);
        }

        for (Block block : handler.getBlocks()) {
            topBlockCollision(block);
            bottomBlockCollision(block);
            sideBlockCollision(block);
        }

        spikeCollision();
    }

    protected void topBlockCollision(Block block) {
        if (getTopBounds().intersects(block.getBounds())) {
            y = block.getY() + Block.HEIGHT;
            speedY = 0;
            isFalling = true;
            if ((block instanceof MysteryBox) && ((MysteryBox) block).isFilled()) {
                handler.getBonuses().add(new Bonus(block.getX() + (float) Block.WIDTH / 2 - (float) Bonus.WIDTH / 2,
                        block.getY() - Bonus.HEIGHT, handler));
                ((MysteryBox) block).setFilled(false);
            }
        }
    }

    protected void bottomBlockCollision(Block block) {
        if (getBottomBounds().intersects(block.getBounds())) {
            y = block.getY() - getHeight() + 1;
            speedY = 0;
            isFalling = false;
            isJumping = false;
        } else {
            isFalling = true;
        }
    }

    protected void sideBlockCollision(Block block) {
        if (getLeftBounds().intersects(block.getBounds())) {
            x = block.getX() + block.getWidth();
        } else if (getRightBounds().intersects(block.getBounds())) {
            x = block.getX() - getWidth();
        }
    }

    private void spikeCollision() {
        for (Spike spike : handler.getSpikes()) {
            if (getBottomBounds().intersects(spike.getBounds())) {
                healthPoints = 0;
            }
        }
    }

    public Rectangle getTopBounds() {
        return new Rectangle((int) x + 10, (int) y,
                getWidth() - 20, getHeight() / 2);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) x + 10, (int) y + getHeight() / 2,
                getWidth() - 20, getHeight() / 2);
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int) x, (int) y + 6,
                10, getHeight() - 12);
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int) x + getWidth() - 10, (int) y + 6,
                10, getHeight() - 12);
    }

    protected abstract void drawAnimation(Graphics2D graphics2D);

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if ((healthPoints <= 0) && !isDead()) {
            setDead(true);
            healthPoints = 0;
            deathTime = System.currentTimeMillis();
            speedX = 0;
            speedY = 0;
        }

        if (!isDead()) {
            if (isFalling || isJumping) {
                speedY += GRAVITY;
            }

            if (speedX > MAX_SPEED) {
                speedX = MAX_SPEED;
            } else if (speedX < -MAX_SPEED) {
                speedX = -MAX_SPEED;
            }

            if (speedY > MAX_SPEED) {
                speedY = MAX_SPEED;
            } else if (speedY < -MAX_SPEED) {
                speedY = -MAX_SPEED;
            }
        }

        collision();
    }

    public boolean getIsJumping() {
        return isJumping;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void getDamage(int damagePoints) {
        healthPoints -= damagePoints;
    }

    public boolean mustBeRemoved() {
        return isDead() && ((System.currentTimeMillis() - deathTime) >= 2000);
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public boolean isGettingDamage() {
        return isGettingDamage;
    }

    public void setGettingDamage(boolean gettingDamage) {
        isGettingDamage = gettingDamage;
    }

    public long getTimerShooting() {
        return timerShooting;
    }

    public void setTimerShooting(long timerShooting) {
        this.timerShooting = timerShooting;
    }

    public long getTimerGettingDamage() {
        return timerGettingDamage;
    }

    public void setTimerGettingDamage(long timerGettingDamage) {
        this.timerGettingDamage = timerGettingDamage;
    }
}
