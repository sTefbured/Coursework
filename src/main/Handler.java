package main;

import gameObjects.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    private Player player;
    private List<Enemy> enemies;
    private List<MysteryBox> mysteryBoxes;
    private List<Block> blocks;
    private List<Bonus> bonuses;
    private List<Spike> spikes;
    private List<Bullet> bullets;
    private List<LevelEnd> levelEnds;

    public Handler() {
        enemies = new ArrayList<>();
        mysteryBoxes = new ArrayList<>();
        blocks = new ArrayList<>();
        bonuses = new ArrayList<>();
        spikes = new ArrayList<>();
        bullets = new ArrayList<>();
        levelEnds = new ArrayList<>();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<MysteryBox> getMysteryBoxes() {
        return mysteryBoxes;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public List<Spike> getSpikes() {
        return spikes;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<LevelEnd> getLevelEnds() {
        return levelEnds;
    }

    public void addPlayer(Player player) {
        if (this.player == null) {
            this.player = player;
        }
    }

    public void update() {
        if (player == null) {
            return;
        }
        player.update();

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        for (int i = 0, size = bonuses.size(); i < size; i++) {
            Bonus bonus = bonuses.get(i);
            bonus.update();
            if (bonus.isDead) {
                bonuses.remove(bonus);
                size--;
                i--;
            }
        }

        for (int i = 0, size = bullets.size(); i < size; i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.isDead) {
                bullets.remove(bullet);
                size--;
                i--;
            }
        }
    }

    public void render(Graphics2D graphics2D) {
        for (Block block : blocks) {
            block.render(graphics2D);
        }

        for (MysteryBox mysteryBox : mysteryBoxes) {
            mysteryBox.render(graphics2D);
        }

        for (Bonus bonus : bonuses) {
            bonus.render(graphics2D);
        }

        for (Enemy enemy : enemies) {
            enemy.render(graphics2D);
        }

        for (Spike spike : spikes) {
            spike.render(graphics2D);
        }

        for (Bullet bullet : bullets) {
            bullet.render(graphics2D);
        }

        if (player == null) {
            System.exit(0);
        }
        player.render(graphics2D);
    }

    public void clearLevel() {
        enemies.clear();
        mysteryBoxes.clear();
        blocks.clear();
        bonuses.clear();
        spikes.clear();
        bullets.clear();
        levelEnds.clear();
        player = null;
    }
}
