package main;

import framework.KeyInput;
import framework.Textures;
import gameObjects.Player;
import menus.*;

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
        HELP,
        CREDITS,
        RUNNING,
        PAUSE,
        GAME_OVER;

        public boolean isChanged;
    }

    public static Font font, font1;
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
    private HelpMenu helpMenu;
    private Credits credits;
    private GameOver gameOver;
    private Pause pause;

    private Game() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE.ttf"));
            font1 = Font.createFont(Font.TRUETYPE_FONT, new File("res/RETRO_SPACE_INV.ttf"));
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
        helpMenu = new HelpMenu(this);
        credits = new Credits(this);
        gameOver = new GameOver(this);
        pause = new Pause(this);


        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, title, iconImage, this);
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
            case MAIN_MENU: {
                loadMainMenu(graphics2D);
                break;
            }
            case LEVELS_LIST: {
                loadLevelsList(graphics2D);
                break;
            }
            case HELP: {
                loadHelp(graphics2D);
                break;
            }
            case CREDITS: {
                loadCredits(graphics2D);
                break;
            }
            case GAME_OVER: {
                gameOver(graphics2D);
                break;
            }
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
                pause();
                continue;
            } else if ((currentState == State.RUNNING) && currentState.isChanged) {
                handler.getPlayer().setAlive();
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
                System.out.println("FPS: " + frames + " updates: " + updates);
                updates = frames = 0;
            }

            if (!isRunning) {
                if (handler.getPlayer().isDead) {
                    handler.getPlayer().isDead = false;
                    currentState = State.GAME_OVER;
                    currentState.isChanged = true;
                } else if (LevelLoader.getCurrentLevel() < LevelLoader.numberOfLevels) {
                    levelLoader.loadLevel();
                    isRunning = true;
                } else {
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

    private void gameOver(Graphics2D graphics2D) {
        if (currentState.isChanged) {
            removeKeyListener(keyInput);
            handler.clearLevel();
            addKeyListener(gameOver);
            currentState.isChanged = false;
        }
        gameOver.render(graphics2D);
    }

    public void pause() {
        if (currentState.isChanged) {
            addKeyListener(pause);
            currentState.isChanged = false;
        }
        pause.render((Graphics2D) bufferStrategy.getDrawGraphics());
        bufferStrategy.show();
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

    private void loadHelp(Graphics2D graphics2D) {
        if (currentState.isChanged) {
            addKeyListener(helpMenu);
            currentState.isChanged = false;
        }
        helpMenu.render(graphics2D);
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