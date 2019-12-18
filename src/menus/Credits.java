package menus;

import framework.Menu;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Credits extends Menu {
    private Game game;

    public Credits(Game game) {
        super(20, Game.WINDOW_HEIGHT - 50, Game.font, Color.GREEN, Color.WHITE, new String[] { "BACK" });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        graphics2D.setColor(Color.ORANGE);
        graphics2D.setFont(Game.font.deriveFont(Font.PLAIN, 20));
        graphics2D.drawString("Developer: Kotikov Stepan", Game.WINDOW_WIDTH / 3, Game.WINDOW_HEIGHT / 2);
        renderButtons(buttons.length, graphics2D);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pressButton();
        }
    }

    @Override
    protected void pressButton() {
        game.removeKeyListener(this);
        game.currentState = Game.State.MAIN_MENU;
        game.currentState.isChanged = true;
    }
}
