package menus;

import framework.Animation;
import framework.Menu;
import framework.Textures;
import gameObjects.Player;
import main.Game;
import main.LevelLoader;

import java.awt.*;
import java.io.File;

public class MainMenu extends Menu {
    private Game game;
    private Animation animation;
    private Textures textures;

    public MainMenu(int x, int y, Font font, Color buttonColor, Color activeButtonColor, Game game, Textures textures) {
        super(x, y, font, buttonColor, activeButtonColor, new String[]{
                "START",
                "STAGES",
                "HELP",
                "CREDITS",
                "EXIT"
        });

        this.game = game;
        this.textures = textures;
        animation = new Animation();
    }

    @Override
    public void render(Graphics2D graphics2D) {
        //background
        graphics2D.setColor(new Color(51, 40, 255));
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

        renderTitle(graphics2D);

        renderButtons(buttons.length, graphics2D);
        graphics2D.drawImage(animation.getSpriteImage(30, true, textures.getPlayerDieRight()),
                Game.WINDOW_WIDTH / 2 + 200 - Player.IMG_DELTA, Game.WINDOW_HEIGHT / 2,
                Player.WIDTH + 2 * Player.IMG_DELTA, Player.HEIGHT, null);
    }

    private void renderTitle(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GREEN);
        graphics2D.setFont(font.deriveFont(Font.PLAIN, 100));
        graphics2D.drawString(Game.title, 80, 200);
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(Game.font1.deriveFont(Font.PLAIN, 100));
        graphics2D.drawString(Game.title, 80, 200);
    }

    @Override
    protected void pressButton() {
        switch (currentChoice) {
            case 0: {
                game.currentState = Game.State.RUNNING;
                LevelLoader.setCurrentLevel(0);
                Game.isRunning = true;
                break;
            }
            case 1: {
                game.currentState = Game.State.LEVELS_LIST;
                break;
            }
            case 2: {
                game.currentState = Game.State.HELP;
                break;
            }
            case 3: {
                game.currentState = Game.State.CREDITS;
                break;
            }
            case 4: {
                System.exit(0);
                break;
            }
        }
        game.currentState.isChanged = true;
        game.removeKeyListener(this);
    }
}
