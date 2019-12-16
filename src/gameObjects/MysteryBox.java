package gameObjects;

import java.awt.*;

public class MysteryBox extends Block {
    public boolean isFilled;

    public MysteryBox(float x, float y) {
        super(x, y);
        isFilled = true;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.MAGENTA);
        graphics2D.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}
