package framework;

import gameObjects.Player;
import main.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Player player = handler.getPlayer();
        if ((player == null) || (player.isDead) || (player.isGettingDamage)) {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                player.setSpeedX(5);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.setSpeedX(-5);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if ((!player.isJumping) && (player.speedY == 0)) {
                    player.setSpeedY(-14);
                    player.setFalling(false);
                    player.setJumping(true);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_CONTROL:
                player.shoot();
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Player player = handler.getPlayer();
        if ((player == null) || (player.isGettingDamage)) {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                player.setSpeedX(0);
                break;
        }
    }
}