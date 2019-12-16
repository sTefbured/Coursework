package framework;

import gameObjects.Block;
import gameObjects.Bonus;
import gameObjects.MysteryBox;
import main.Handler;

import java.awt.*;

public abstract class Sprite extends MovingObject {
    protected final float GRAVITY = 0.5f;

    protected Animation animation;
    protected boolean isFalling;
    protected boolean isJumping;

    protected Sprite(float x, float y, Handler handler) {
        super(x, y, handler);
        animation = new Animation();
        speedX = 0;
        speedY = 0;
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
    }

    protected void topBlockCollision(Block block) {
        if (getTopBounds().intersects(block.getBounds())) {
            y = block.getY() + Block.HEIGHT;
            speedY = 0;
            isFalling = true;
            if ((block instanceof MysteryBox) && ((MysteryBox) block).isFilled) {
                handler.getBonuses().add(new Bonus(block.getX() + (float) Block.WIDTH / 2 - (float) Bonus.WIDTH / 2, block.getY() - Bonus.HEIGHT, handler));
                ((MysteryBox) block).isFilled = false;
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

        collision();
    }
}
