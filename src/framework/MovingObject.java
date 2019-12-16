package framework;

import main.Handler;

public abstract class MovingObject extends GameObject {
    protected final int MAX_SPEED = 25;

    protected float speedX, speedY;
    protected Handler handler;
    public boolean isDead;

    public MovingObject(float x, float y, Handler handler) {
        super(x, y);
        isDead = false;
        this.handler = handler;
    }

    public void setSpeedX(float speed) {
        speedX = speed;
    }

    public void setSpeedY(float speed) {
        speedY = speed;
    }

    protected abstract void collision();

    public abstract void update();
}
