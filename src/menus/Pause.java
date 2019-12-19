package menus;

import framework.Menu;
import main.Game;

import java.awt.*;

public class Pause extends Menu {
    private Game game;
    public Pause(Game game) {
        super(Game.WINDOW_WIDTH / 2 - 180, Game.WINDOW_HEIGHT / 2 - 40, Game.font.deriveFont(Font.PLAIN, 24),
                Color.RED, Color.GREEN, new String[] {
                        "RESUME",
                        "BACK TO MENU",
                        "QUIT"
                });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(Game.WINDOW_WIDTH / 2 - 200, Game.WINDOW_HEIGHT / 2 - 200, 400, 300);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(Game.font.deriveFont(Font.BOLD, 35));
        graphics2D.drawString("PAUSE", Game.WINDOW_WIDTH / 2 - 70, Game.WINDOW_HEIGHT / 2 - 130);
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(Game.font1.deriveFont(Font.BOLD, 35));
        graphics2D.drawString("PAUSE", Game.WINDOW_WIDTH / 2 - 70, Game.WINDOW_HEIGHT / 2 - 130);
        renderButtons(3, graphics2D);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0: {
                game.currentState = Game.State.RUNNING;
                break;
            }
            case 1: {
                game.currentState = Game.State.MAIN_MENU;
                game.removeKeyListener(this);

                Game.isRunning = false;
                break;
            }
            case 2: {
                System.exit(0);
                break;
            }
        }
        game.currentState.isChanged = true;
    }
}
