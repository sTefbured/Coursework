package gameObjects;

// int red = (rgb >> 16) & 0xff;
// int green = (rgb >> 8) & 0xff;
// int blue = rgb & 0xff;
public interface ObjectColor {
    int PLAYER = 0xff00ff00;

    int BLOCK = 0xffffffff;
    int BLOCK_UP = 0xff8793ff;
    int BLOCK_DOWN = 0xff433dff;
    int BLOCK_LEFT = 0xff77ff3d;
    int BLOCK_RIGHT = 0xff42ffe8;
    int BLOCK_DOUBLE_HORIZONTAL = 0x1;
    int BLOCK_DOUBLE_VERTICAL = 0x0;
    int BLOCK_UP_LEFT = 0xff777746;
    int BLOCK_UP_RIGHT = 0xff750c70;
    int BLOCK_DOWN_LEFT = 0xfffff600;
    int BLOCK_DOWN_RIGHT = 0xffff0054;
    int BLOCK_IN_DOWN_LEFT = 0xff602a16;
    int BLOCK_IN_UP_LEFT = 0xff4a2d7a;
    int BLOCK_IN_UP_RIGHT = 0xff3f5d7a;
    int BLOCK_IN_DOWN_RIGHT = 0xff1a601b;

    int MYSTERY_BOX = 0xff0000ff;
    int ENEMY = 0xffff0000;
    int SPIKE = 0xffff00ff;
    int LVL_END = 0xffff5400;
}
