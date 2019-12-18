package gameObjects;

public class MysteryBox extends Block {
    public boolean isFilled;

    public MysteryBox(float x, float y) {
        super(x, y, ObjectColor.MYSTERY_BOX);
        isFilled = true;
    }
}
