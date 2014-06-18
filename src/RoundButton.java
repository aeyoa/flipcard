import com.sun.media.sound.SoftTuning;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by arsenykogan on 09/06/14.
 */
public class RoundButton {

    private final PApplet p;
    private PImage image;
    private int xPos;
    private int yPos;
    private float screenX;
    private float screenY;

    public RoundButton(final PApplet p, final PImage image, final int xPos, final int yPos) {
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
        double distance = Math.sqrt(Math.pow(mouseX - screenX, 2) + Math.pow(mouseY - screenY, 2));
        if (distance <= image.width / 2) {
            return true;
        }
        return false;
    }

    public void perform() {
        System.out.println("add new card");
    }
}
