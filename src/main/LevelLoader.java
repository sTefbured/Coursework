package main;

import gameobjects.*;
import lombok.extern.log4j.Log4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Log4j
public class LevelLoader implements ObjectColor {
    private static BufferedImage[] levelImages;
    private static int numberOfLevels;
    private static int currentLevel;

    static {
        try {
            levelImages = new BufferedImage[]{
                    ImageIO.read(LevelLoader.class.getResource("/levels/lvl_1.png")),
                    ImageIO.read(LevelLoader.class.getResource("/levels/lvl_2.png"))
            };
        } catch (Exception e) {
            log.fatal(e.toString());
        }
        numberOfLevels = levelImages.length;
        currentLevel = 0;
    }

    private Handler handler;
    private Camera camera;

    public LevelLoader(Handler handler, Camera camera) {
        this.handler = handler;
        this.camera = camera;
        this.camera = new Camera(0);
    }

    public static int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void loadLevel() {
        parseLvlImg(levelImages[currentLevel]);
        handler.getPlayer().setSpeedX(0);
        currentLevel++;
    }

    private void parseLvlImg(BufferedImage lvlImage) {
        int width = lvlImage.getWidth();
        int height = lvlImage.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = lvlImage.getRGB(i, j);
                switchObjects(rgb, i, j);
            }
        }
    }

    private void switchObjects(int rgb, int i, int j) {
        switch (rgb) {
            case PLAYER:
                if (handler.getPlayer() == null) {
                    handler.addPlayer(new Player(i * Block.WIDTH, j * Block.HEIGHT, handler));
                } else {
                    handler.getPlayer().setCoordinates(i * Block.WIDTH, j * Block.HEIGHT);
                }
                camera.setX((int) handler.getPlayer().getX());
                handler.getPlayer().setCamera(camera);
                break;
            case BLOCK:
            case BLOCK_DOWN:
            case BLOCK_UP:
            case BLOCK_RIGHT:
            case BLOCK_LEFT:
            case BLOCK_UP_LEFT:
            case BLOCK_UP_RIGHT:
            case BLOCK_DOWN_LEFT:
            case BLOCK_DOWN_RIGHT:
            case BLOCK_DOUBLE_HORIZONTAL:
            case BLOCK_DOUBLE_VERTICAL:
            case BLOCK_IN_DOWN_LEFT:
            case BLOCK_IN_DOWN_RIGHT:
            case BLOCK_IN_UP_LEFT:
            case BLOCK_IN_UP_RIGHT:
                handler.getBlocks().add(new Block(i * Block.WIDTH, j * Block.HEIGHT, rgb));
                break;
            case MYSTERY_BOX:
                handler.getMysteryBoxes().add(new MysteryBox(i * Block.WIDTH, j * Block.HEIGHT));
                break;
            case ENEMY:
                handler.getEnemies().add(new Enemy(i * Block.WIDTH, j * Block.HEIGHT, handler));
                break;
            case SPIKE:
                handler.getSpikes().add(new Spike(i * Block.WIDTH, j * Block.HEIGHT + Block.HEIGHT / 2.0f));
                break;
            case LVL_END:
                handler.getLevelEnds().add(new LevelEnd(i * Block.WIDTH, j * Block.HEIGHT));
                break;
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        LevelLoader.currentLevel = currentLevel;
    }
}