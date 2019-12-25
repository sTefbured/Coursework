package menus;

import framework.Menu;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HelpMenu extends Menu {
    private Game game;
    private String[] keys = new String[] {
            "\"W\" or \"ARROW_UP\" or \"SPACE\" to jump",
            "\"D\" or \"ARROW_RIGHT\" to run right",
            "\"A\" or \"ARROW_LEFT\" to run left",
            "\"Ctrl\" to shoot",
            "\"Esc\" to pause"
    };

    public HelpMenu(Game game) {
        super(20, Game.WINDOW_HEIGHT - 50, Game.font, Color.GREEN, Color.WHITE, new String[] { "BACK" });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        graphics2D.setColor(Color.ORANGE);
        graphics2D.setFont(Game.font1.deriveFont(Font.PLAIN, 30));
        for (int i = 0; i < keys.length; i++) {
            graphics2D.drawString(keys[i], Game.WINDOW_WIDTH / 3 - 100, 300 + i * 40);
        }
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
