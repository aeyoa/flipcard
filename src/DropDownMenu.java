/**
 * Created by arsenykogan on 17/06/14.
 */
public class DropDownMenu {

    private static final int HIDDEN_Y_POS = -50;
    private static final int ACTIVE_Y_POS = 50;

    private final Flipcard app;

    private final EasingValue menuButtonAngle = new EasingValue(0.15, 0.01);
    private final Button menuButton;
    private boolean isActive;

    private final Button saveButton;
    private final Button learnedButton;
    private final Button shuffleButton;

    private final EasingValue saveButtonY = new EasingValue(0.30, 1);
    private final EasingValue shuffleButtonY = new EasingValue(0.20, 1);
    private final EasingValue clearButtonY = new EasingValue(0.10, 1);

    public DropDownMenu(final Flipcard app) {
        this.app = app;
        this.menuButton = new Button(app, app.loadImage("menu.png"), 750, ACTIVE_Y_POS);
        isActive = false;

        this.saveButtonY.setTarget(HIDDEN_Y_POS);
        this.shuffleButtonY.setTarget(HIDDEN_Y_POS);
        this.clearButtonY.setTarget(HIDDEN_Y_POS);

        this.saveButton = new Button(app, app.loadImage("save.png"), 690, (int) saveButtonY.getCurrentValue());
        this.shuffleButton = new Button(app, app.loadImage("shuffle.png"), 610, (int) shuffleButtonY.getCurrentValue());
        this.learnedButton = new Button(app, app.loadImage("flip-all.png"), 520, (int) clearButtonY.getCurrentValue());
    }

    public void display() {
        menuButtonAngle.update();
        saveButtonY.update();
        shuffleButtonY.update();
        clearButtonY.update();
        saveButton.setY((int) saveButtonY.getCurrentValue());
        shuffleButton.setY((int) shuffleButtonY.getCurrentValue());
        learnedButton.setY((int) clearButtonY.getCurrentValue());
        saveButton.display();
        shuffleButton.display();
        learnedButton.display();
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
            app.saveToJSON();
        }
        if (learnedButton.isPressed()) {
            /* Important: isToggled represents current state (this frame) of the button! *//*
            if (learnedButton.isToggled()) {
                app.focusToLearned();
            } else {
                app.focusToNew();
            }*/
            app.getCardsCollection().flipAllToA();
        }
        if (shuffleButton.isPressed()) {
            app.getCardsCollection().shuffleCards();
        }
    }

    private void activate() {
        saveButtonY.setTarget(ACTIVE_Y_POS);
        shuffleButtonY.setTarget(ACTIVE_Y_POS);
        clearButtonY.setTarget(ACTIVE_Y_POS);

        isActive = true;
        menuButtonAngle.setTarget(-app.HALF_PI);
    }

    private void deactivate() {
        saveButtonY.setTarget(HIDDEN_Y_POS);
        shuffleButtonY.setTarget(HIDDEN_Y_POS);
        clearButtonY.setTarget(HIDDEN_Y_POS);
        isActive = false;
        menuButtonAngle.setTarget(0);
    }


}
