package framework;

import java.awt.image.BufferedImage;

public class Animation {
    private int index;
    private int count;
    private BufferedImage spriteImage;

    public Animation() {
        index = 0;
        count = 0;
    }

    public BufferedImage getSpriteImage(int speed, boolean isLooped, BufferedImage[] spriteImages) {
        if (!isLooped && (index >= spriteImages.length)) {
            return spriteImage;
        }

        if (count > speed) {
            count = 0;
            index = 0;
        } else if ((count == 0) && (index == 0)) {
            spriteImage = spriteImages[index];
        }

        if (index >= spriteImages.length) {
            index = 0;
        }

        if (count == speed) {
            spriteImage = spriteImages[index];
            index++;
            count = 0;
        }

        count++;
        return spriteImage;
    }
}