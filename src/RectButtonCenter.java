import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by arsenykogan on 09/06/14.
 */
public class RectButtonCenter {

    private final PApplet p;
    private PImage image;
    private int xPos;
    private int yPos;
    private float screenX;
    private float screenY;

    public RectButtonCenter(final PApplet p, final PImage image, final int xPos, final int yPos) {
        this.p = p;
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void display() {
        this.display(0);
    }

    public void display(final float rotationAngle) {
        p.pushMatrix();

        p.imageMode(p.CENTER);
        p.translate(xPos, yPos, 0);
        p.rotateZ(rotationAngle);
        p.image(image, 0, 0);
        p.popMatrix();

        screenX = p.screenX(xPos, yPos);
        screenY = p.screenY(xPos, yPos);
    }

    public boolean isPressed() {
        int mouseX = p.mouseX;
        int mouseY = p.mouseY;
        if (mouseX < (screenX - image.width / 2) || mouseX > (screenX + image.width / 2) ||
                mouseY < (screenY - image.height / 2) || mouseY > (screenY + image.height / 2)) {
            return false;
        }
        return true;
    }

    public void setY(final int yPos) {
        this.yPos = yPos;
    }

    public void perform() {
        System.out.println("add new card");
    }
}
