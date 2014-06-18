import processing.core.PApplet;

/**
 * Created by arsenykogan on 17/06/14.
 */
public class DropDownMenu {

    private static final int HIDDEN_Y_POS = -50;
    private static final int ACTIVE_Y_POS = 50;

    private final PApplet pApplet;

    private final EasingValue menuButtonAngle = new EasingValue(0.1, 0.01);
    private final RoundButton menuButton;
    private boolean isActive;

    public DropDownMenu(final PApplet pApplet) {
        this.pApplet = pApplet;
        this.menuButton = new RoundButton(pApplet, pApplet.loadImage("menu.png"), 750, ACTIVE_Y_POS);
        isActive = false;
    }

    public void display() {
        menuButtonAngle.update();
        menuButton.display((float) menuButtonAngle.getCurrentValue());
    }

    public void mouseClicked() {
        if (menuButton.isPressed()) {
            if (isActive) {
                deactivate();
            } else {
                activate();
            }
        }
    }

    private void activate() {
        isActive = true;
        menuButtonAngle.setTarget(-pApplet.HALF_PI);
    }

    private void deactivate() {
        isActive = false;
        menuButtonAngle.setTarget(0);
    }


}
