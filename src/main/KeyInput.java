package main;

import gameobjects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Game game;
    private Handler handler;

    public KeyInput(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Player player = handler.getPlayer();
        if ((player == null) || (player.isDead()) || (player.isGettingDamage())) {
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
                if ((!player.getIsJumping()) && (player.getSpeedY() == 0)) {
                    player.setSpeedY(-14);
                    player.setFalling(false);
                    player.setJumping(true);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                game.setCurrentState(Game.State.PAUSE);
                game.getCurrentState().setChanged(true);
                break;
            case KeyEvent.VK_CONTROL:
                if (!player.isShooting()) {
                    player.shoot();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Player player = handler.getPlayer();
        if ((player == null) || (player.isGettingDamage())) {
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