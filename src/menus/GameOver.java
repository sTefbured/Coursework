package menus;

import framework.Menu;
import main.Game;
import main.LevelLoader;

import java.awt.*;

public class GameOver extends Menu {
    private Game game;

    public GameOver(Game game) {
        super(Game.WINDOW_WIDTH / 2 - 100, Game.WINDOW_HEIGHT / 2, Game.font,
                Color.yellow, Color.GREEN, new String[] {
                        "TRY AGAIN",
                        "BACK TO MENU"
                });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(Game.WINDOW_WIDTH / 2 - 200, Game.WINDOW_HEIGHT / 2 - 100, 400, 200);
        graphics2D.drawString("GAME OVER", Game.WINDOW_WIDTH / 3, 200);
        renderButtons(2, graphics2D);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0: {
                LevelLoader.setCurrentLevel(LevelLoader.getCurrentLevel() - 1);
                game.currentState = Game.State.RUNNING;
                Game.isRunning = true;
                break;
            }
            case 1: {
                game.currentState = Game.State.MAIN_MENU;
                break;
            }
        }
        game.currentState.isChanged = true;
        game.removeKeyListener(this);
    }
}
