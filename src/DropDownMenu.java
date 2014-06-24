/**
 * Created by arsenykogan on 17/06/14.
 */
public class DropDownMenu {

    private static final int HIDDEN_Y_POS = -50;
    private static final int ACTIVE_Y_POS = 50;

    private final Flipcard pApplet;

    private final EasingValue menuButtonAngle = new EasingValue(0.15, 0.01);
    private final Button menuButton;
    private boolean isActive;

    private final Button saveButton;
    private final Button clearButton;
    private final Button shuffleButton;

    private final EasingValue saveButtonY = new EasingValue(0.30, 1);
    private final EasingValue shuffleButtonY = new EasingValue(0.20, 1);
    private final EasingValue clearButtonY = new EasingValue(0.10, 1);

    public DropDownMenu(final Flipcard pApplet) {
        this.pApplet = pApplet;
        this.menuButton = new Button(pApplet, pApplet.loadImage("menu.png"), 750, ACTIVE_Y_POS);
        isActive = false;

        this.saveButtonY.setTarget(HIDDEN_Y_POS);
        this.shuffleButtonY.setTarget(HIDDEN_Y_POS);
        this.clearButtonY.setTarget(HIDDEN_Y_POS);

        this.saveButton = new Button(pApplet, pApplet.loadImage("save.png"), 700, (int) saveButtonY.getCurrentValue());
        this.shuffleButton = new Button(pApplet, pApplet.loadImage("shuffle.png"), 630, (int) shuffleButtonY.getCurrentValue());
        this.clearButton = new Button(pApplet, pApplet.loadImage("show-learned.png"), 525, (int) clearButtonY.getCurrentValue());
    }

    public void display() {
        menuButtonAngle.update();
        saveButtonY.update();
        shuffleButtonY.update();
        clearButtonY.update();
        saveButton.setY((int) saveButtonY.getCurrentValue());
        shuffleButton.setY((int) shuffleButtonY.getCurrentValue());
        clearButton.setY((int) clearButtonY.getCurrentValue());
        saveButton.display();
        shuffleButton.display();
        clearButton.display();
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
        if (saveButton.isPressed()) {
            pApplet.saveToJSON();
        }
    }

    private void activate() {
        saveButtonY.setTarget(ACTIVE_Y_POS);
        shuffleButtonY.setTarget(ACTIVE_Y_POS);
        clearButtonY.setTarget(ACTIVE_Y_POS);

        isActive = true;
        menuButtonAngle.setTarget(-pApplet.HALF_PI);
    }

    private void deactivate() {
        saveButtonY.setTarget(HIDDEN_Y_POS);
        shuffleButtonY.setTarget(HIDDEN_Y_POS);
        clearButtonY.setTarget(HIDDEN_Y_POS);
        isActive = false;
        menuButtonAngle.setTarget(0);
    }


}
