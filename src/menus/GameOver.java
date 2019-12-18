package menus;

import framework.GameObject;
import framework.Menu;
import main.Game;

import java.awt.*;

public class GameOver extends Menu {
    private Game game;

    public GameOver(Game game) {
        super(Game.WINDOW_WIDTH / 2, Game.WINDOW_HEIGHT / 2, Game.font,
                Color.yellow, Color.GREEN, new String[] {
                        "TRY AGAIN",
                        "BACK TO MENU"
                });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawString("GAME OVER", Game.WINDOW_WIDTH / 3, 200);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0: {
                game.currentState = Game.State.RUNNING;
            }
            case 1: {
                game.currentState = Game.State.MAIN_MENU;
            }
        }
    }
}
