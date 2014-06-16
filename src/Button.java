import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by arsenykogan on 09/06/14.
 */
public class Button {

    private final PApplet p;
    private PImage image;
    private int xPos;
    private int yPos;

    public Button(final PApplet p, final PImage image, final int xPos, final int yPos) {
        this.p = p;
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void display() {
        p.imageMode(p.CORNER);
        p.image(image, xPos, yPos);
    }

    public boolean isPressed() {
        int mouseX = p.mouseX;
        int mouseY = p.mouseY;
        if (mouseX < p.screenX(xPos, yPos) || mouseX > (p.screenX(xPos, yPos) + image.width) ||
                mouseY < p.screenY(xPos, yPos) || mouseY > (p.screenY(xPos, yPos) + image.height)) {
            return false;
        }
        return true;
    }

    public void perform() {
        System.out.println("add new card");
    }

}
