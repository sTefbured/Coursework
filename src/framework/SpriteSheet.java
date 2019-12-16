package framework;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage image;

    public SpriteSheet(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage(col * width, row * height, width, height);
    }
}
