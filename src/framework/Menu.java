package framework;

import main.LevelLoader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class Menu extends KeyAdapter {
    private int x, y;
    protected Font font;
    protected Color buttonColor, activeButtonColor;
    protected String[] buttons;
    protected int currentChoice;

    protected Menu(int x, int y, Font font, Color buttonColor, Color activeButtonColor, String[] buttons) {
        this.x = x;
        this.y = y;
        this.font = font;
        this.buttonColor = buttonColor;
        this.activeButtonColor = activeButtonColor;
        this.buttons = buttons;
        currentChoice = 0;
    }

    protected void renderButtons(int buttonsCount, Graphics2D graphics2D) {
        for (int i = 0; i < buttonsCount; i++) {
            if (i == currentChoice) {
                graphics2D.setFont(font.deriveFont(Font.PLAIN, 40));
                graphics2D.setColor(activeButtonColor);
            } else {
                graphics2D.setFont(font.deriveFont(Font.PLAIN, 30));
                graphics2D.setColor(buttonColor);
            }
            graphics2D.drawString(buttons[i], x, y + i * 50);
        }
    }

    public abstract void render(Graphics2D graphics2D);

    protected abstract void pressButton();

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
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
        }
    }
}
