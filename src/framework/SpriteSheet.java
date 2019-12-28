package framework;

import lombok.extern.log4j.Log4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Log4j
public class SpriteSheet {
    private BufferedImage image;

    public SpriteSheet(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage(col * width, row * height, width, height);
    }
}
