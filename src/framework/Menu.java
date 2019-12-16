package framework;

import framework.Animation;
import framework.Textures;
import gameObjects.Player;
import main.Game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class Menu extends KeyAdapter {
    private Font font;
    private Game game;
    private String[] buttons;
    private int currentChoice;
    private Animation animation;
    private Textures textures;

    private enum State {
        MAIN_MENU,
        LEVELS,
        CREDITS
    }

    private State currentState;

    public Menu(Game game, Textures textures) {
        super();
        currentState = State.MAIN_MENU;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE.ttf"));
        } catch (Exception e) {
            font = Font.getFont("Arial");
            e.printStackTrace();
        }

        this.game = game;
        buttons = new String[]{
                "START",
                "LEVELS",
                "CREDITS",
                "EXIT"
        };
        currentChoice = 0;
        animation = new Animation();
        this.textures = textures;
    }

    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(51, 40, 255));
        graphics2D.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

        switch (currentState) {
            case MAIN_MENU:
                renderButtons(graphics2D);
                graphics2D.drawImage(animation.getSpriteImage(20, true, textures.getPlayerRunRight()),
                        Game.WINDOW_WIDTH / 2 + 200, Game.WINDOW_HEIGHT / 2,
                        Player.WIDTH, Player.HEIGHT, null);
                break;
            case LEVELS:
                renderLevelsGrid(graphics2D);
                break;
            case CREDITS:
                renderCredits(graphics2D);
                break;
        }
    }

    private void renderButtons(Graphics2D graphics2D) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == currentChoice) {
                graphics2D.setFont(font.deriveFont(Font.PLAIN, 40));
                graphics2D.setColor(Color.GREEN);
            } else {
                graphics2D.setFont(font.deriveFont(Font.PLAIN, 30));
                graphics2D.setColor(Color.RED);
            }
            graphics2D.drawString(buttons[i], 200, Game.WINDOW_HEIGHT / 2 + i * 50);
        }
    }

    private void renderCredits(Graphics2D graphics2D) {
        System.out.println("CREDITS");
    }

    private void renderLevelsGrid(Graphics2D graphics2D) {
        System.out.println("LEVELS GRID");
    }

    private void pressButton() {
        switch (currentChoice) {
            case 0: {
                game.isRunning = true;
                break;
            }
            case 1: {
                currentState = State.LEVELS;
                break;
            }
            case 2: {
                currentState = State.CREDITS;
                break;
            }
            case 3: {
                System.exit(0);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN: {
                currentChoice++;
                if (currentChoice >= buttons.length) {
                    currentChoice = 0;
                }
                break;
            }
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP: {
                currentChoice--;
                if (currentChoice < 0) {
                    currentChoice = buttons.length - 1;
                }
                break;
            }
            case KeyEvent.VK_ENTER: {
                pressButton();
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                switch (currentState) {
                    case LEVELS:
                    case CREDITS: {
                        currentState = State.MAIN_MENU;
                        break;
                    }
                }
                break;
            }
        }
    }
}
