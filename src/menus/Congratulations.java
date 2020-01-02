package menus;

import framework.Menu;
import main.Game;

import java.awt.*;

public class Congratulations extends Menu {
    private Game game;

    public Congratulations(Game game) {
        super(Game.WINDOW_WIDTH / 2 - 120, Game.WINDOW_HEIGHT / 2 + 50, Game.getFont1(), Color.WHITE, Color.GREEN,
                new String[] {
                        "MAIN MENU",
                        "QUIT"
                });
        this.game = game;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.CYAN);
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        renderButtons(2, graphics2D);
        renderTitle(graphics2D);

    }

    private void renderTitle(Graphics2D graphics2D) {
        graphics2D.setFont(Game.getFont1().deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("GAME IS FINISHED!", 20, 200);
        graphics2D.setFont(Game.getFont2().deriveFont(Font.PLAIN, 100));
        graphics2D.setColor(Color.RED);
        graphics2D.drawString("GAME IS FINISHED!", 20, 200);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0:
                game.removeKeyListener(this);
                game.setCurrentState(Game.State.MAIN_MENU);
                game.getCurrentState().setChanged(true);
                break;
            case 1:
                System.exit(0);
                break;
        }
    }
}
