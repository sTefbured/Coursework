package main;

import framework.KeyInput;
import framework.Textures;
import gameObjects.Block;
import gameObjects.Player;
import menus.Credits;
import menus.GameOver;
import menus.LevelsList;
import menus.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

public class Game extends Canvas {
    public static final int WINDOW_WIDTH = 1080, WINDOW_HEIGHT = 830; // 30x25 blocks
    public static final String title = "My platformer";
    private static final Image iconImage = new ImageIcon("res/icon.png").getImage();
    private static final Image[] background = new Image[]{
            new ImageIcon("res/bglayer0.png").getImage(),
            new ImageIcon("res/bglayer1.png").getImage(),
            new ImageIcon("res/bglayer2.png").getImage(),
    };

    public enum State {
        MAIN_MENU,
        LEVELS_LIST,
        CREDITS,
        RUNNING,
        PAUSE,
        GAME_OVER;

        public boolean isChanged;
    }

    public static Font font;
    public static boolean isRunning;
    private Handler handler;
    private Camera camera;
    private static Textures textures;
    private LevelLoader levelLoader;
    BufferStrategy bufferStrategy;
    public State currentState;
    private KeyInput keyInput;

    private MainMenu mainMenu;
    private LevelsList levelsList;
    private Credits credits;
    private GameOver gameOver;

    private Game() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRunning = false;
        textures = new Textures();
        handler = new Handler(this);
        levelLoader = new LevelLoader(handler, camera, this);
        currentState = State.MAIN_MENU;
        currentState.isChanged = true;

        mainMenu = new MainMenu(200, Game.WINDOW_HEIGHT / 2, font,
                Color.RED, Color.GREEN, this, textures);
        levelsList = new LevelsList(this);
        credits = new Credits(this);
        gameOver = new GameOver(this);


        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, title, iconImage, this);
    }

    public void startMenu() {
        while (true) {
            if (getBufferStrategy() == null) {
                createBufferStrategy(3);
            }
            bufferStrategy = getBufferStrategy();
            requestFocus();
            System.out.println(currentState);
            while (!isRunning) {
                showMenu();
            }
            run();
        }
    }

    private void showMenu() {
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        switch (currentState) {
            case MAIN_MENU: {
                loadMainMenu(graphics2D);
                break;
            }
            case LEVELS_LIST: {
                loadLevelsList(graphics2D);
                break;
            }
            case CREDITS: {
                loadCredits(graphics2D);
                break;
            }
            case RUNNING: {
                break;
            }
            case PAUSE: {
                break;
            }
            case GAME_OVER: {
                break;
            }
        }
        bufferStrategy.show();
    }

    private void initGameObjects() {
        keyInput = new KeyInput(handler);
        addKeyListener(keyInput);
        levelLoader.loadLevel();
        camera = levelLoader.getCamera();
    }

    private void run() {
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
                updateLevel();
                updates++;
                delta--;
            }
            renderLevel();
            frames++;

            if (System.nanoTime() - time >= oneSecond) {
                time += oneSecond;
                System.out.println("FPS: " + frames + " updates: " + updates);
                updates = frames = 0;
            }

            if (!isRunning) {
                if (handler.getPlayer().isDead) {
                    gameOver();
                } else if (LevelLoader.getCurrentLevel() < LevelLoader.numberOfLevels) {
                    handler.clearLevel();
                    levelLoader.loadLevel();
                    isRunning = true;
                } else {
                    removeKeyListener(keyInput);
                    handler.clearLevel();
                    currentState.isChanged = true;
                }
            }
        }
    }

    private void updateLevel() {
        handler.update();
    }

    private void renderLevel() {
        if (handler.getPlayer() == null) {
            return;
        }
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics2D.translate(camera.getX(), camera.getY());

        graphics2D.drawImage(background[0],
                -WINDOW_WIDTH / 2 + Player.WIDTH / 2 + (int) handler.getPlayer().getX(), 0, null);

        handler.render(graphics2D);
        bufferStrategy.show();
    }

    public static Textures getTextures() {
        return textures;
    }

    private void gameOver() {
//        if (currentState.isChanged) {
//            addKeyListener(gameOver);
//        }
//        gameOver.render((Graphics2D) bufferStrategy.getDrawGraphics());

        currentState = State.MAIN_MENU;
        currentState.isChanged = true;
        removeKeyListener(keyInput);
        handler.clearLevel();
    }

    private void loadMainMenu(Graphics2D graphics2D) {
        if (currentState.isChanged) {
            addKeyListener(mainMenu);
            currentState.isChanged = false;
        }
        mainMenu.render(graphics2D);
    }

    private void loadLevelsList(Graphics2D graphics2D) {
        if (currentState.isChanged) {
            addKeyListener(levelsList);
            currentState.isChanged = false;
        }
        levelsList.render(graphics2D);
    }

    private void loadCredits(Graphics2D graphics2D) {
        if (currentState.isChanged) {
            addKeyListener(credits);
            currentState.isChanged = false;
        }
        credits.render(graphics2D);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startMenu();
    }
}