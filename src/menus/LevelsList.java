package menus;

import framework.Menu;
import main.Game;
import main.LevelLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LevelsList extends Menu {
    private Game game;

    public LevelsList(Game game) {
        super(Game.WINDOW_WIDTH / 2, Game.WINDOW_HEIGHT / 2,
                Game.font, Color.PINK, Color.YELLOW, new String[LevelLoader.numberOfLevels + 1]);
        for (int i = 0; i < buttons.length - 1; i++) {
            buttons[i] = "LEVEL " + (i + 1);
        }
        buttons[buttons.length - 1] = "BACK";
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        renderButtons(LevelLoader.numberOfLevels, graphics2D);

        if (buttons.length - 1 == currentChoice) {
            graphics2D.setFont(font.deriveFont(Font.PLAIN, 40));
            graphics2D.setColor(activeButtonColor);
        } else {
            graphics2D.setFont(font.deriveFont(Font.PLAIN, 30));
            graphics2D.setColor(buttonColor);
        }
        graphics2D.drawString(buttons[buttons.length - 1], 20, Game.WINDOW_HEIGHT - 50);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.currentState = Game.State.MAIN_MENU;
            game.currentState.isChanged = true;
            game.removeKeyListener(this);
        }
    }

    @Override
    protected void pressButton() {
        game.removeKeyListener(this);
        if (currentChoice == buttons.length - 1) {
            game.currentState = Game.State.MAIN_MENU;
            game.currentState.isChanged = true;
            return;
        }
        LevelLoader.setCurrentLevel(currentChoice);
        game.currentState = Game.State.RUNNING;
        game.currentState.isChanged = true;
        Game.isRunning = true;
    }
}