import processing.core.PApplet;

/**
 * Created by arsenykogan on 17/06/14.
 */
public class ButtonArea {

    private final PApplet p;
    private int x1Pos;
    private int y1Pos;
    private int x2Pos;
    private int y2Pos;

    public ButtonArea(final PApplet p, final int upperLeftXPos, final int upperLeftYPos, final int bottomRightXPos, final int bottomRightYPos) {
        this.p = p;
        this.x1Pos = upperLeftXPos;
        this.y1Pos = upperLeftYPos;
        this.x2Pos = bottomRightXPos;
        this.y2Pos = bottomRightYPos;
    }

    public boolean isPressed() {
        int mouseX = p.mouseX;
        int mouseY = p.mouseY;
        if (mouseX < x1Pos || mouseX > x2Pos ||
                mouseY < y1Pos || mouseY > y2Pos) {
            return false;
        }
        return true;
    }
}
