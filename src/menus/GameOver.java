package menus;

import framework.Menu;
import main.Game;
import main.LevelLoader;

import java.awt.*;

public class GameOver extends Menu {
    private Game game;

    public GameOver(Game game) {
        super(Game.WINDOW_WIDTH / 2 - 130, Game.WINDOW_HEIGHT / 2 - 30, Game.getFont1(),
                Color.yellow, Color.GREEN, new String[] {
                        "TRY AGAIN",
                        "BACK TO MENU",
                        "QUIT"
                });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(Game.WINDOW_WIDTH / 2 - 200, Game.WINDOW_HEIGHT / 2 - 100, 400, 200);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(Game.getFont1().deriveFont(Font.BOLD, 35));
        graphics2D.drawString("GAME OVER", Game.WINDOW_WIDTH / 2 - 100, Game.WINDOW_HEIGHT / 2 - 130);
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(Game.getFont2().deriveFont(Font.BOLD, 35));
        graphics2D.drawString("GAME OVER", Game.WINDOW_WIDTH / 2 - 100, Game.WINDOW_HEIGHT / 2 - 130);
        renderButtons(3, graphics2D);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0:
                LevelLoader.setCurrentLevel(LevelLoader.getCurrentLevel() - 1);
                game.setCurrentState(Game.State.RUNNING);
                Game.setRunning(true);
                game.setPlayerAlive();
                break;
            case 1:
                game.setCurrentState(Game.State.MAIN_MENU);
                break;
            case 2:
                System.exit(0);
                break;
        }
        game.getCurrentState().setChanged(true);
        game.removeKeyListener(this);
    }
}
