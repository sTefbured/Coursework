package main;

import gameObjects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class LevelLoader implements ObjectColors {
    private Handler handler;

    private Graphics2D graphics2D;
    private BufferStrategy bufferStrategy;

    private Camera camera;
    private BufferedImage[] levelImages;
    private int currentLevel;

    public LevelLoader(Handler handler, BufferStrategy bufferStrategy, Camera camera) {
        this.handler = handler;
        this.bufferStrategy = bufferStrategy;
        this.camera = camera;

        try {
            levelImages = new BufferedImage[]{
                    ImageIO.read(getClass().getResource("/levels/lvl_1.png"))
//                    ImageIO.read(getClass().getResource("/levels/lvl_2.png")),
//                    ImageIO.read(getClass().getResource("/levels/lvl_3.png")),
//                    ImageIO.read(getClass().getResource("/levels/lvl_4.png")),
//                    ImageIO.read(getClass().getResource("/levels/lvl_5.png"))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentLevel = 0;
    }

    public void loadLevel() {
        parseLvlImg(levelImages[currentLevel]);
        currentLevel++;
    }

    private void parseLvlImg(BufferedImage lvlImage) {
        int width = lvlImage.getWidth();
        int height = lvlImage.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = lvlImage.getRGB(i, j);
                //                int red = (rgb >> 16) & 0xff;
//                int green = (rgb >> 8) & 0xff;
//                int blue = rgb & 0xff;
                switchObjects(rgb, i, j);
            }
            graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
            graphics2D.setStroke(new BasicStroke(40));
            graphics2D.drawLine(Game.WINDOW_WIDTH / 2 - width, Game.WINDOW_HEIGHT - 100,
                    Game.WINDOW_WIDTH / 2 - width + i * 2, Game.WINDOW_HEIGHT - 100);
            bufferStrategy.show();
        }
    }

    private void switchObjects(int rgb, int i, int j) {
        switch (rgb) {
            case PLAYER: {
                camera = new Camera(i * Block.WIDTH);
                handler.addPlayer(new Player(i * Block.WIDTH, j * Block.HEIGHT, handler, camera));
                break;
            }
            case BLOCK: {
                handler.getBlocks().add(new Block(i * Block.WIDTH, j * Block.HEIGHT));
                break;
            }
            case MYSTERY_BOX: {
                handler.getMysteryBoxes().add(new MysteryBox(i * Block.WIDTH, j * Block.HEIGHT));
                break;
            }
            case ENEMY: {
                handler.getEnemies().add(new Enemy(i * Block.WIDTH, j * Block.HEIGHT, handler));
                break;
            }
            case SPIKE: {
                handler.getSpikes().add(new Spike(i * Block.WIDTH, j * Block.HEIGHT + Block.HEIGHT / 2));
                break;
            }
            case LVL_END: {
                handler.getLevelEnds().add(new LevelEnd(i * Block.WIDTH, j * Block.HEIGHT));
                break;
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }
}