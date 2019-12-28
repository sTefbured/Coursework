package gameobjects;

import framework.GameObject;

import java.awt.*;

public class Block extends GameObject {
    public static final int HEIGHT = 32;
    public static final int WIDTH = 32;

    protected int blockType;

    public Block(float x, float y, int blockType) {
        super(x, y);
        this.blockType = blockType;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        int index = 0;
        switch (blockType) {
            case ObjectColor.BLOCK:
                index = 0;
                break;
            case ObjectColor.BLOCK_DOWN:
                index = 1;
                break;
            case ObjectColor.BLOCK_LEFT:
                index = 2;
                break;
            case ObjectColor.BLOCK_RIGHT:
                index = 3;
                break;
            case ObjectColor.BLOCK_UP:
                index = 4;
                break;
            case ObjectColor.BLOCK_DOUBLE_HORIZONTAL:
                index = 5;
                break;
            case ObjectColor.BLOCK_DOUBLE_VERTICAL:
                index = 6;
                break;
            case ObjectColor.BLOCK_UP_LEFT:
                index = 7;
                break;
            case ObjectColor.BLOCK_UP_RIGHT:
                index = 8;
                break;
            case ObjectColor.BLOCK_DOWN_LEFT:
                index = 9;
                break;
            case ObjectColor.BLOCK_DOWN_RIGHT:
                index = 10;
                break;
            case ObjectColor.BLOCK_IN_DOWN_LEFT:
                index = 11;
                break;
            case ObjectColor.BLOCK_IN_UP_LEFT:
                index = 12;
                break;
            case ObjectColor.BLOCK_IN_UP_RIGHT:
                index = 13;
                break;
            case ObjectColor.BLOCK_IN_DOWN_RIGHT:
                index = 14;
                break;
            case ObjectColor.MYSTERY_BOX:
                index = 15;
                break;
        }
        graphics2D.drawImage(textures.getBlocks()[index], (int) x, (int) y, null);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }
}
