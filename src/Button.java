import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by arsenykogan on 09/06/14.
 */
public class Button {

    private final PApplet pApplet;
    private PImage image;
    private int xPos;
    private int yPos;
    private float screenX;
    private float screenY;

    /* TOGGLE BUTTON */
    /* By default button isn't toggle. */
    private boolean isToggleButton;
    private PImage toggleImage;
    private boolean toggled;

    /* NORMAL AND HOVER STATE */
    /* By default opacity is 10% and hover opacity is 40% */
    private int defaultColor;
    private int hoverColor;

    public Button(final PApplet p, final PImage image, final int xPos, final int yPos) {
        this.pApplet = p;
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;

        /* Default and hover opacity */
        defaultColor = pApplet.color(0, (float) (255 * 0.2));
        hoverColor = pApplet.color(0, (float) (255 * 0.3));

        /* For toggle button */
        this.isToggleButton = false;
        this.toggleImage = null;
        this.toggled = false;
    }

    public void display() {
        this.display(0);
    }

    public void display(final float rotationAngle) {
        pApplet.pushMatrix();
        pApplet.imageMode(pApplet.CENTER);
        pApplet.translate(xPos, yPos, 0);
        pApplet.rotateZ(rotationAngle);
        /* Tint image */
        if (mouseOver()) {
            pApplet.tint(hoverColor);
        } else {
            pApplet.tint(defaultColor);
        }
        if (!toggled) {
            pApplet.image(image, 0, 0);
        } else {
            pApplet.image(toggleImage, 0, 0);
        }
        pApplet.noTint();
        pApplet.popMatrix();

        /* Updating the position of the button on the screen. */
        screenX = pApplet.screenX(xPos, yPos);
        screenY = pApplet.screenY(xPos, yPos);
    }

    /* Image to display when button is toggled */
    public void makeToggle(final PImage toggleImage) {
        this.toggleImage = toggleImage;
        isToggleButton = true;
    }

    public void setHoverColor(final int hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setDefaultColor(final int defaultColor) {
        this.defaultColor = defaultColor;
    }

    /* Invoked when mousePressed */
    public boolean isPressed() {
        if (mouseOver()) {
            /* Changing the state of button */
            if (isToggleButton) {
                toggled = !toggled;
            }
            return true;
        }
        return false;
    }

    private boolean mouseOver() {
        int mouseX = pApplet.mouseX;
        int mouseY = pApplet.mouseY;
        if (mouseX < (screenX - image.width / 2) || mouseX > (screenX + image.width / 2) ||
                mouseY < (screenY - image.height / 2) || mouseY > (screenY + image.height / 2)) {
            return false;
        }
        return true;
    }

    public void setY(final int yPos) {
        this.yPos = yPos;
    }

    public boolean isToggled() {
        return toggled;
    }
}
