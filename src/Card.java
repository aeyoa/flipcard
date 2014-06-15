import processing.core.PApplet;

/**
 * Created by arsenykogan on 25/05/14.
 */
public class Card {


    private static int TEXT_COLOR;
    private static int CARD_COLOR;
    private static int CARD_WIDTH = 400;
    private static int CARD_HEIGHT = 200;
    private static float EASING = 0.1f;
    private static int STEP = 550;
    private static float STEP_EASING = 0.2f;
    private Button removeButton;
    private Button editButton;
    private Button learnedButton;

    private final PApplet p;
    private float angle;
    private float targetAngle;
    private int xPos = 400;
    private int yPos = 200;
    private int xPosTarget;
    private int yPosTarget;
    private EasingValue editLayerHeight;

    /* Words on card sides. */
    private String sideA = "";
    private String sideB = "";
    /* sideA = false, sideB = true. */
    private Boolean side;
    private boolean isEditing;

    public Card(final PApplet p) {
        this.p = p;
        /* Initialize colors. */
        TEXT_COLOR = p.color(255);
        CARD_COLOR = p.color(162, 162, 162);
        this.angle = 0;
        this.targetAngle = 0;
        this.xPosTarget = xPos;

        yPos = -100;
        this.yPosTarget = yPos;
        this.side = false;
        this.isEditing = true;
        editLayerHeight = new EasingValue(0.09, 0.01);
        editLayerHeight.setTarget(40);

        /* Create buttons */
        int buttonsY = 70;
        removeButton = new Button(p, p.loadImage("resources/removeButton.png"), -60 - 10, buttonsY);
        editButton = new Button(p, p.loadImage("resources/editButton.png"), 0 - 10, buttonsY);
        learnedButton = new Button(p, p.loadImage("resources/learnedButton.png"), 60 - 10, buttonsY);

        p.fill(TEXT_COLOR);
        p.textSize(40);
    }

    public Card(final PApplet p, final String sideA, final String sideB) {
        this(p);
        this.sideA = sideA;
        this.sideB = sideB;
    }

    public void display() {
        p.pushMatrix();
            /* Filling card with CARD_COLOR. */
        p.fill(CARD_COLOR);
        p.noStroke();

        /* Smoothing X position. */
        if (Math.abs(xPosTarget - xPos) >= 1) {
            xPos += (xPosTarget - xPos) * STEP_EASING;
        }

        /* Smoothing X position. */
        if (Math.abs(yPosTarget - yPos) >= 1) {
            yPos += (yPosTarget - yPos) * STEP_EASING;
        }

        p.translate(xPos, yPos, 0);

        /* Smoothing rotation angle. */
        if ((targetAngle - angle) > .01) {
            angle += (targetAngle - angle) * EASING;
        } else {
            angle = targetAngle;
        }
        p.rotateY(angle);
        p.rectMode(p.CENTER);
        p.rect(0, 0, CARD_WIDTH, CARD_HEIGHT);

        checkMousePos();
        drawEditLayer();

        p.textAlign(p.CENTER, p.CENTER);
        /*Translate text a bit higher. */
        p.translate(0, -5, 0);
        p.translate(0, 0, 2);

        drawText(sideA);

        p.translate(0, 0, -4);
        p.rotateY(p.PI);

        drawText(sideB);
        p.popMatrix();
    }

    public void turn() {
        targetAngle += p.PI;
        side = !side;
    }

    public void moveRight() {
        xPosTarget += STEP;
    }

    public void moveLeft() {
        xPosTarget -= STEP;
    }

    public void appear() {
        yPosTarget = 200;
    }

    public void disappear() {
        yPosTarget = 600;
    }

    public String getCurrentSide() {
        return (side) ? sideB : sideA;
    }

    public String getSideA() {
        return sideA;
    }

    public String getSideB() {
        return sideB;
    }

    public void setCurrentSide(final String text) {
        if (this.side) {
            this.sideB = text;
        } else {
            this.sideA = text;
        }
    }

    private void drawText(final String text) {
        p.fill(TEXT_COLOR);
        p.text(text, 0, 0, 0);
        if (isEditing && (Math.abs(angle - targetAngle) < 0.05) && (p.millis() / 700 % 2 == 0)) {
            float width = p.textWidth(text);
            p.stroke(127);
            p.strokeWeight(2);
            p.line(width / 2, 20, 0, width / 2, -20, 0);
        }
    }

    private void drawEditLayer() {
        p.rectMode(p.BOTTOM);
        p.fill(255, 255, 255, 127);
        editLayerHeight.update();
        p.rect(-CARD_WIDTH / 2, CARD_HEIGHT / 2, CARD_WIDTH / 2, CARD_HEIGHT / 2 - (float) editLayerHeight.getCurrentValue());

        if (editLayerHeight.getCurrentValue() > 35) {
            removeButton.display();
            editButton.display();
            if (editButton.isPressed()) {
                this.isEditing = true;
            }
            learnedButton.display();
        }
    }

    private void checkMousePos() {
        if (p.mouseX > 200 && p.mouseX < 600 && p.mouseY > 100 && p.mouseY < 300) {
            editLayerHeight.setTarget(45);
        }
    }


    public void editText(final char key) {
        if (isEditing && key != p.CODED) {
            if (key == p.BACKSPACE) {
                this.setCurrentSide(this.getCurrentSide().substring(0, Math.max(0, this.getCurrentSide().length() - 1)));
            } else if (key == p.ENTER || key == p.RETURN) {
                this.turn();
            } else {
                this.setCurrentSide(this.getCurrentSide() + key);
            }
        }
    }

    public void endEditing() {
        editLayerHeight.setTarget(0);
        isEditing = false;
    }


    @Override
    public String toString() {
        return "Card{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
