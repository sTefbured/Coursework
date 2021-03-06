package framework;

import lombok.extern.log4j.Log4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Log4j
public class Textures {
    private BufferedImage[] playerAttackLeft;
    private BufferedImage[] playerAttackRight;
    private BufferedImage[] playerIdleLeft;
    private BufferedImage[] playerIdleRight;
    private BufferedImage[] playerJumpLeft;
    private BufferedImage[] playerJumpRight;
    private BufferedImage[] playerDieLeft;
    private BufferedImage[] playerDieRight;
    private BufferedImage[] playerRunLeft;
    private BufferedImage[] playerRunRight;

    private BufferedImage[] enemyAttackLeft;
    private BufferedImage[] enemyAttackRight;
    private BufferedImage[] enemyDieLeft;
    private BufferedImage[] enemyDieRight;
    private BufferedImage[] enemyWalkLeft;
    private BufferedImage[] enemyWalkRight;

    private BufferedImage[] bulletLeft;
    private BufferedImage[] bulletRight;

    private BufferedImage[] blocks;
    private BufferedImage spikes;
    private BufferedImage bonus;

    public Textures() {
        playerAttackLeft = getSpriteArray("/player/player_attack_left.png");
        playerAttackRight = getSpriteArray("/player/player_attack_right.png");
        playerIdleLeft = getSpriteArray("/player/player_idle_left.png");
        playerIdleRight = getSpriteArray("/player/player_idle_right.png");
        playerJumpLeft = getSpriteArray("/player/player_jump_left.png");
        playerJumpRight = getSpriteArray("/player/player_jump_right.png");
        playerDieLeft = getSpriteArray("/player/player_die_left.png");
        playerDieRight = getSpriteArray("/player/player_die_right.png");
        playerRunLeft = getSpriteArray("/player/player_run_left.png");
        playerRunRight = getSpriteArray("/player/player_run_right.png");

        enemyAttackLeft = getSpriteArray("/enemy/enemy_attack_left.png");
        enemyAttackRight = getSpriteArray("/enemy/enemy_attack_right.png");
        enemyDieLeft = getSpriteArray("/enemy/enemy_die_left.png");
        enemyDieRight = getSpriteArray("/enemy/enemy_die_right.png");
        enemyWalkLeft = getSpriteArray("/enemy/enemy_walk_left.png");
        enemyWalkRight = getSpriteArray("/enemy/enemy_walk_right.png");

        bulletLeft = getSpriteArray("/bullet/bullet_left.png");
        bulletRight = getSpriteArray("/bullet/bullet_right.png");

        blocks = getSpriteArray("/blocks/blocks.png");

        try {
            spikes = ImageIO.read(getClass().getResource("/spikes.png"));
            bonus = ImageIO.read(getClass().getResource("/bonus.png"));
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    private BufferedImage[] getSpriteArray(String path) {
        SpriteSheet spriteSheet = new SpriteSheet(path);
        int size = spriteSheet.getImage().getWidth() / 32;
        BufferedImage[] spriteArray = new BufferedImage[size];

        for (int i = 0; i < size; i++) {
            spriteArray[i] = spriteSheet.grabImage(i, 0, 32, 32);
        }

        return spriteArray;
    }

    public BufferedImage[] getPlayerAttackLeft() {
        return playerAttackLeft;
    }

    public BufferedImage[] getPlayerAttackRight() {
        return playerAttackRight;
    }

    public BufferedImage[] getPlayerIdleLeft() {
        return playerIdleLeft;
    }

    public BufferedImage[] getPlayerIdleRight() {
        return playerIdleRight;
    }

    public BufferedImage[] getPlayerJumpLeft() {
        return playerJumpLeft;
    }

    public BufferedImage[] getPlayerJumpRight() {
        return playerJumpRight;
    }

    public BufferedImage[] getPlayerDieLeft() {
        return playerDieLeft;
    }

    public BufferedImage[] getPlayerDieRight() {
        return playerDieRight;
    }

    public BufferedImage[] getPlayerRunLeft() {
        return playerRunLeft;
    }

    public BufferedImage[] getPlayerRunRight() {
        return playerRunRight;
    }

    public BufferedImage[] getEnemyAttackLeft() {
        return enemyAttackLeft;
    }

    public BufferedImage[] getEnemyAttackRight() {
        return enemyAttackRight;
    }

    public BufferedImage[] getEnemyDieLeft() {
        return enemyDieLeft;
    }

    public BufferedImage[] getEnemyDieRight() {
        return enemyDieRight;
    }

    public BufferedImage[] getEnemyWalkLeft() {
        return enemyWalkLeft;
    }

    public BufferedImage[] getEnemyWalkRight() {
        return enemyWalkRight;
    }

    public BufferedImage[] getBulletLeft() {
        return bulletLeft;
    }

    public BufferedImage[] getBulletRight() {
        return bulletRight;
    }

    public BufferedImage[] getBlocks() {
        return blocks;
    }

    public BufferedImage getSpikes() {
        return spikes;
    }

    public BufferedImage getBonus() {
        return bonus;
    }
}
