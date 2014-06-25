import processing.core.PApplet;

/**
 * Created by arsenykogan on 16/06/14.
 */
public class EditLayer {

    /* Sizes */
    private static final int HEIGHT = 40;
    private static final int HEIGHT_THRESHOLD = 30;
    private static final double EASING = 0.1;
    private static final double DELTA = 0.05;
    private static final int CARD_HEIGHT = 200;
    private static final int CARD_BOTTOM_Y = CARD_HEIGHT / 2;

    /* Icons */
    private static final String removeButtonFilename = "remove.png";
    private static final String editButtonFilename = "edit.png";
    private static final String learnedButtonFilename = "learned.png";
    private final Button removeButton;
    private final Button editButton;
    private final Button learnedButton;
    private static final int buttonsY = -40;

    private final PApplet pApplet;
    private final Card card;
    private boolean isActive = false;
    private EasingValue currentHeight = new EasingValue(EASING, DELTA);
    private boolean justCreated;

    public EditLayer(final PApplet pApplet, final Card card) {
        this.pApplet = pApplet;
        this.card = card;
        this.activate();
        justCreated = true;
        removeButton = new Button(pApplet, pApplet.loadImage(removeButtonFilename), -50, CARD_BOTTOM_Y - buttonsY);
        removeButton.setHoverColor(pApplet.color(255, 0, 0, 255));
        editButton = new Button(pApplet, pApplet.loadImage(editButtonFilename), 0, CARD_BOTTOM_Y - buttonsY);
        learnedButton = new Button(pApplet, pApplet.loadImage(learnedButtonFilename), 50, CARD_BOTTOM_Y - buttonsY);
    }

    public void display() {
        checkForHover();
        currentHeight.update();
        pApplet.rectMode(pApplet.CORNERS);
        pApplet.fill(255, 127);
        pApplet.noStroke();
        if (isActive) {
            /* Show icons */
            pApplet.translate(0, 0, 1);
            removeButton.display();
            editButton.display();
            learnedButton.display();
            pApplet.translate(0, 0, -1);
        }

    }

    public void mouseClicked() {
        if (isActive) {
            /* Check for clicks */
            if (removeButton.isPressed()) {
                card.getCollection().removeFocusCard();
            }
            if (editButton.isPressed()) {
                card.startEditing();
            }
        }
    }


    public void activate() {
        isActive = true;

    }

    public void deactivate() {
        isActive = false;
        if (justCreated) {
            justCreated = false;
        }
    }

    private void checkForHover() {
        if (pApplet.mouseX > pApplet.screenX(-200, 0) && pApplet.mouseX < pApplet.screenX(200, 0)
                && pApplet.mouseY > pApplet.screenY(0, 0) && pApplet.mouseY < pApplet.screenY(0, 200)) {
            activate();
        } else if (!justCreated) {
            deactivate();
        }
    }

}
