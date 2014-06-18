import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by arsenykogan on 17/06/14.
 */
public class FakeButton {

    private final PApplet p;
    private int x1Pos;
    private int y1Pos;
    private int x2Pos;
    private int y2Pos;

    public FakeButton(final PApplet p, final int upperLeftXPos, final int upperLeftYPos, final int bottomRightXPpos, final int bottomRightYPpos) {
        this.p = p;
        this.x1Pos = upperLeftXPos;
        this.y1Pos = upperLeftYPos;
        this.x2Pos = bottomRightXPpos;
        this.y2Pos = bottomRightYPpos;
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
