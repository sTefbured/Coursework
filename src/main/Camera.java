package main;

import gameObjects.Player;

public class Camera {
    private int x, y; //coordinates on the screen

    public Camera(int x) {
        setX(x);
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = -x - Player.WIDTH / 2 + Game.WINDOW_WIDTH / 2;
    }

    public void setY(int y) {
        this.y = -y + Game.WINDOW_HEIGHT;
    }
}
