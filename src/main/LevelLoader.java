package main;

import gameObjects.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LevelLoader implements ObjectColor {
    private Handler handler;

    private Camera camera;
    private Game game;
    private BufferedImage[] levelImages;
    public static int numberOfLevels;
    private static int currentLevel;

    public LevelLoader(Handler handler, Camera camera, Game game) {
        this.handler = handler;
        this.camera = camera;
        this.camera = new Camera(0);
        this.game = game;
        try {
            levelImages = new BufferedImage[]{
                    ImageIO.read(getClass().getResource("/levels/lvl_1.png")),
                    ImageIO.read(getClass().getResource("/levels/lvl_2.png"))
//                    ImageIO.read(getClass().getResource("/levels/lvl_3.png")),
//                    ImageIO.read(getClass().getResource("/levels/lvl_4.png")),
//                    ImageIO.read(getClass().getResource("/levels/lvl_5.png"))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        numberOfLevels = levelImages.length;
        currentLevel = 0;
    }

    public void loadLevel() {
        parseLvlImg(levelImages[currentLevel]);
        currentLevel++;
        System.out.println(currentLevel);
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
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = rgb & 0xff;
        if ((red == 74) && (green == 45) && (blue == 122)) {
            System.out.println("SKA");
            System.out.println(Integer.toHexString(rgb));
        }
        switch (rgb) {
            case PLAYER: {
                handler.addPlayer(new Player(i * Block.WIDTH, j * Block.HEIGHT, handler));
                camera.setX((int) handler.getPlayer().getX());
                handler.getPlayer().setCamera(camera);
                break;
            }
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
            case BLOCK_IN_UP_RIGHT:{
                handler.getBlocks().add(new Block(i * Block.WIDTH, j * Block.HEIGHT, rgb));
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

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        LevelLoader.currentLevel = currentLevel;
    }
}