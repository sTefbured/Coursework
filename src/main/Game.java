package main;

import framework.KeyInput;
import framework.Menu;
import framework.Textures;
import gameObjects.Block;
import gameObjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

public class Game extends Canvas {
    public static final int WINDOW_WIDTH = 30 * Block.WIDTH, WINDOW_HEIGHT = 830; // 30x25 blocks
    private static final String title = "My platformer";
    private static final Image iconImage = new ImageIcon("res/icon.png").getImage();
    private static final Image[] background = new Image[]{
            new ImageIcon("res/bglayer0.png").getImage(),
            new ImageIcon("res/bglayer1.png").getImage(),
            new ImageIcon("res/bglayer2.png").getImage(),
    };

    //    private Thread thread;
    public static Font font;
    public boolean isRunning;
    private Handler handler;
    private Camera camera;
    private static Textures textures;
    private LevelLoader levelLoader;
    private int currentLevel;
    BufferStrategy bufferStrategy;

    private Game() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE_INV.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRunning = false;
        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, title, iconImage, this);
    }

    public void startMenu() {
        if (this.getBufferStrategy() == null) {
            this.createBufferStrategy(3);
        }
        bufferStrategy = this.getBufferStrategy();
        requestFocus();
        textures = new Textures();
        framework.Menu menu = new Menu(this, textures);
//        this.createBufferStrategy(3);
        this.addKeyListener(menu);
        while (!isRunning) {
            Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
            menu.render(graphics2D);
            graphics2D.dispose();
            bufferStrategy.show();
        }
        run();
    }

    private void initGameObjects() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        levelLoader = new LevelLoader(handler, bufferStrategy,  camera);
        levelLoader.loadLevel();
        camera = levelLoader.getCamera();
    }

    //    @Override
    private void run() {
        requestFocus();
        initGameObjects();
        long lastTime = System.nanoTime();
        long currentTime;
        final double oneSecond = 1000000000;
        double updatesPerSec = 60;
        double secPerUpdate = oneSecond / updatesPerSec;
        double delta = 0;
        long time = lastTime;
        int updates = 0;
        int frames = 0;

        while (isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / secPerUpdate;
            lastTime = currentTime;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.nanoTime() - time >= oneSecond) {
                time += oneSecond;
                System.out.println("FPS: " + frames + " updates: " + updates);
                updates = frames = 0;
            }
        }
    }

    private void update() {
        handler.update();
    }

    private void render() {
        if (handler.getPlayer() == null) {
            return;
        }
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics2D.translate(camera.getX(), camera.getY());

        graphics2D.drawImage(background[0],
                -WINDOW_WIDTH / 2 + Player.WIDTH / 2 + (int) handler.getPlayer().getX(), 0, null);

        handler.render(graphics2D);
        graphics2D.setColor(Color.cyan);
        graphics2D.dispose();

        bufferStrategy.show();
    }

    public static Textures getTextures() {
        return textures;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public static void main(String[] args) {
        new Game();
    }
}