package gameobjects;

public class MysteryBox extends Block {
    private boolean isFilled;

    public MysteryBox(float x, float y) {
        super(x, y, ObjectColor.MYSTERY_BOX);
        isFilled = true;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
