import processing.core.PApplet;

/**
 * Created by arsenykogan on 25/05/14.
 */
public class Card {


    private transient int TEXT_COLOR;
    private transient int CARD_COLOR;
    private transient int CARD_WIDTH = 400;
    private transient int CARD_HEIGHT = 200;
    private transient float EASING = 0.1f;
    private transient int STEP = 550;
    private transient float STEP_EASING = 0.2f;

    private transient final PApplet p;
    private transient float angle;
    private transient float targetAngle;
    private transient int xPos = 400;
    private transient int yPos = 200;
    private transient int xPosTarget;

    /* Words on card sides. */
    private String sideA = "";
    private String sideB = "";
    /* sideA = false, sideB = true. */
    private Boolean side;

    public Card(final PApplet p) {
        this.p = p;
            /* Initialize colors. */
        this.TEXT_COLOR = p.color(20);
        this.CARD_COLOR = p.color(253, 231, 251);
        this.angle = 0;
        this.targetAngle = 0;
        this.xPosTarget = xPos;
        this.side = false;
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

        p.translate(xPos, yPos, 0);

            /* Smoothing rotation angle. */
        if ((targetAngle - angle) > .01) {
            angle += (targetAngle - angle) * EASING;
        }
        p.rotateY(angle);
        p.rectMode(p.CENTER);
        p.rect(0, 0, CARD_WIDTH, CARD_HEIGHT);

        p.fill(TEXT_COLOR);
        p.textSize(40);
        p.textAlign(p.CENTER, p.CENTER);

            /*Translate text a bit higher. */
        p.translate(0, -5, 0);
        p.translate(0, 0, 2);
        p.text(sideA, 0, 0, 0);
        p.translate(0, 0, -4);
        p.rotateY(p.PI);
        p.text(sideB, 0, 0, 0);
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

    public String getSideB() {
        return sideB;
    }

    public String getSideA() {
        return sideA;
    }

    public void setSideB(final String sideB) {
        this.sideB = sideB;
    }

    public void setSideA(final String sideA) {
        this.sideA = sideA;
    }

    public String getCurrentSide() {
        return (side) ? sideB : sideA;
    }

    public void setCurrentSide(final String text) {
        if (this.side) {
            this.sideB = text;
        } else {
            this.sideA = text;
        }
    }


    @Override
    public String toString() {
        return "Card{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
