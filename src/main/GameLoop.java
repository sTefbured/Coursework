package main;

import lombok.extern.log4j.Log4j;

import java.awt.*;

@Log4j
public class GameLoop {
    private Game game;
    private long lastTime;
    private double delta;
    private int updates;

    public GameLoop(Game game) {
        this.game = game;
    }

    public void startLoop() {
        lastTime = System.nanoTime();
        long currentTime;
        final double oneSecond = 1000000000;
        double updatesPerSec = 60;
        double secPerUpdate = oneSecond / updatesPerSec;
        delta = 0;
        long time = lastTime;
        updates = 0;
        int frames = 0;

        while (Game.isRunning()) {
            if (isOnPause()) {
                continue;
            }

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / secPerUpdate;
            lastTime = currentTime;
            update();
            game.renderLevel();
            frames++;

            if (System.nanoTime() - time >= oneSecond) {
                time += oneSecond;
                log.debug("FPS: " + frames + " updates: " + updates);
                updates = frames = 0;
            }

            if (!Game.isRunning()) {
                if (game.getHandler().getPlayer().isDead()) {
                    gameOver();
                } else if (LevelLoader.getCurrentLevel() < LevelLoader.getNumberOfLevels()) {
                    nextLevel();
                } else {
                    gameFinished();
                }
            }
        }
    }

    private boolean isOnPause() {
        if (game.getCurrentState() == Game.State.PAUSE) {
            if (game.getCurrentState().isChanged()) {
                game.removeKeyListener(game.getKeyInput());
            }
            game.loadMenu(game.getPause(), (Graphics2D) game.getBufStrategy().getDrawGraphics());
            game.getBufStrategy().show();
            return true;
        } else if ((game.getCurrentState() == Game.State.RUNNING) && game.getCurrentState().isChanged()) {
            game.removeKeyListener(game.getPause());
            game.addKeyListener(game.getKeyInput());
            game.getCurrentState().setChanged(false);
            lastTime = System.nanoTime();
        }
        return false;
    }

    private void update() {
        while (delta >= 1) {
            game.updateLevel();
            updates++;
            delta--;
        }
    }

    private void gameOver() {
        game.getHandler().getPlayer().setDead(false);
        game.setCurrentState(Game.State.GAME_OVER);
        game.getCurrentState().setChanged(true);
        game.removeKeyListener(game.getKeyInput());
        game.getHandler().clearLevel();
    }

    private void nextLevel() {
        game.getLevelLoader().loadLevel();
        Game.setRunning(true);
    }

    //TODO: Add "congratulations" menu, maybe scores
    private void gameFinished() {
        game.removeKeyListener(game.getKeyInput());
        game.getHandler().clearLevel();
        game.setCurrentState(Game.State.MAIN_MENU);
        game.getCurrentState().setChanged(true);
    }
}
