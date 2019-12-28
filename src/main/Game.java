package main;

import framework.Menu;
import framework.Textures;
import gameobjects.Player;
import lombok.extern.log4j.Log4j;
import menus.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

@Log4j
public class Game extends Canvas {
    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 830; // 30x25 blocks
    public static final String TITLE = "My platformer";
    private static final Image iconImage = new ImageIcon("res/icon.png").getImage();
    private static final Image[] background = new Image[]{
            new ImageIcon("res/bglayer0.png").getImage(),
            new ImageIcon("res/bglayer1.png").getImage(),
            new ImageIcon("res/bglayer2.png").getImage(),
    };

    private static Game instance;

    private static Font font1;
    private static Font font2;
    private static boolean isRunning;
    private static Textures textures;

    static {
        try {
            font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE.ttf"));
            font2 = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE_INV.ttf"));
        } catch (Exception e) {
            log.fatal(e.toString());
        }
        textures = new Textures();
    }

    private Handler handler;
    private Camera camera;
    private LevelLoader levelLoader;
    BufferStrategy bufferStrategy;
    private State currentState;
    private KeyInput keyInput;

    private MainMenu mainMenu;
    private LevelsList levelsList;
    private HelpMenu helpMenu;
    private Credits credits;
    private GameOver gameOver;
    private Pause pause;

    private Game() {
        handler = new Handler(this);
        levelLoader = new LevelLoader(handler, camera);
        currentState = State.MAIN_MENU;
        currentState.isChanged = true;

        mainMenu = new MainMenu(200, Game.WINDOW_HEIGHT / 2, font1,
                Color.RED, Color.GREEN, this, textures);
        levelsList = new LevelsList(this);
        helpMenu = new HelpMenu(this);
        credits = new Credits(this);
        gameOver = new GameOver(this);
        pause = new Pause(this);

        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE, iconImage, this);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void startMenu() {
        while (true) {
            if (getBufferStrategy() == null) {
                createBufferStrategy(3);
            }
            bufferStrategy = getBufferStrategy();
            requestFocus();
            while (!isRunning) {
                showMenu();
            }
            run();
        }
    }

    private void showMenu() {
        Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        switch (currentState) {
            case MAIN_MENU:
                loadMenu(mainMenu, graphics2D);
                break;
            case LEVELS_LIST:
                loadMenu(levelsList, graphics2D);
                break;
            case HELP:
                loadMenu(helpMenu, graphics2D);
                break;
            case CREDITS:
                loadMenu(credits, graphics2D);
                break;
            case GAME_OVER:
                loadMenu(gameOver, graphics2D);
                break;
        }
        bufferStrategy.show();
    }

    private void initGameObjects() {
        keyInput = new KeyInput(this, handler);
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
            if (currentState == State.PAUSE) {
                if (currentState.isChanged) {
                    removeKeyListener(keyInput);
                }
                loadMenu(pause, (Graphics2D) bufferStrategy.getDrawGraphics());
                bufferStrategy.show();
                continue;
            } else if ((currentState == State.RUNNING) && currentState.isChanged) {
                removeKeyListener(pause);
                addKeyListener(keyInput);
                currentState.isChanged = false;
                lastTime = System.nanoTime();
            }
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
                log.debug("FPS: " + frames + " updates: " + updates);
                updates = frames = 0;
            }

            if (!isRunning) {
                if (handler.getPlayer().isDead()) {
                    handler.getPlayer().setDead(false);
                    currentState = State.GAME_OVER;
                    currentState.isChanged = true;
                    removeKeyListener(keyInput);
                    handler.clearLevel();
                } else if (LevelLoader.getCurrentLevel() < LevelLoader.getNumberOfLevels()) {
                    levelLoader.loadLevel();
                    isRunning = true;
                } else {    //TODO: Add "congratulations" menu, maybe scores
                    removeKeyListener(keyInput);
                    handler.clearLevel();
                    currentState = State.MAIN_MENU;
                    currentState.isChanged = true;
                }
            }
        }
        handler.clearLevel();
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

    private void loadMenu(Menu menu, Graphics2D graphics2D) {
        if (currentState.isChanged) {
            addKeyListener(menu);
            currentState.isChanged = false;
        }
        menu.render(graphics2D);
    }

    public static Font getFont1() {
        return font1;
    }

    public static Font getFont2() {
        return font2;
    }

    public static void  setRunning(boolean isRunning) {
        Game.isRunning = isRunning;
    }

    public void setPlayerAlive() {
        handler.getPlayer().setAlive();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public enum State {
        MAIN_MENU,
        LEVELS_LIST,
        HELP,
        CREDITS,
        RUNNING,
        PAUSE,
        GAME_OVER;

        private boolean isChanged;

        public void setChanged(boolean isChanged) {
            this.isChanged = isChanged;
        }
    }
}