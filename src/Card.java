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
    private static int MAX_TEXT_WIDTH = 300;
    private static int TEXT_SIZE = 40;
    private static int LEARNED_COLOR;

    private final Flipcard app;
    private CardsCollection collection;
    private float angle;
    private float targetAngle;
    private int xPos = 400;
    private int yPos = 200;
    private int xPosTarget;
    private int yPosTarget;
    private final EditLayer editLayer;
    private int currentTextSizeA;
    private int currentTextSizeB;

    private float realX = 0;
    private float realY = 0;
    private float realZ = 0;

    /* Words on card sides. */
    private String sideA = "";
    private String sideB = "";
    /* sideA = false, sideB = true. */
    private Boolean side;
    private boolean isEditing;
    private boolean isLearned;

    public Card(final Flipcard app, final CardsCollection collection) {
        this.app = app;
        this.collection = collection;
        /* Initialize colors. */
        TEXT_COLOR = app.color(42, 58, 72);
        CARD_COLOR = app.color(178, 212, 220);
        LEARNED_COLOR = app.color(0, 255, 0);
        this.angle = 0;
        this.targetAngle = 0;
        this.xPosTarget = xPos;

        yPos = -160;
        this.yPosTarget = yPos;
        this.side = false;
        this.isEditing = true;
        this.editLayer = new EditLayer(app, this);
        this.isLearned = false;

        currentTextSizeA = TEXT_SIZE;
        currentTextSizeB = TEXT_SIZE;
        app.fill(TEXT_COLOR);
    }

    public Card(final Flipcard app, final CardsCollection collection, final String sideA, final String sideB) {
        this(app, collection);
        this.sideA = sideA;
        this.sideB = sideB;
    }

    public void display() {
        app.pushMatrix();

        /* Filling card with CARD_COLOR. */
        app.fill(CARD_COLOR);
        if (isLearned) {
            app.stroke(LEARNED_COLOR);
            app.strokeWeight(3);
        } else {
            app.noStroke();
        }

        /* Smoothing X position. */
        if (Math.abs(xPosTarget - xPos) >= 1) {
            xPos += (xPosTarget - xPos) * STEP_EASING;
        }

        /* Smoothing X position. */
        if (Math.abs(yPosTarget - yPos) >= 1) {
            yPos += (yPosTarget - yPos) * STEP_EASING;
        }

        app.translate(xPos, yPos, 0);

        /* Smoothing rotation angle. */
        if ((targetAngle - angle) > .01) {
            angle += (targetAngle - angle) * EASING;
        } else {
            angle = targetAngle;
        }
        app.rotateY(angle);
        app.rectMode(app.CENTER);
        app.rect(0, 0, CARD_WIDTH, CARD_HEIGHT);

        /* Updating info about real positions */
        realX = app.modelX(0, 0, 0);
        realY = app.modelY(0, 0, 0);
        realZ = app.modelZ(0, 0, 0);


        app.textAlign(app.CENTER, app.CENTER);
        float roundAngle = angle % (app.TWO_PI);
        if (roundAngle < app.HALF_PI || roundAngle > (app.HALF_PI + app.PI)) {
            app.translate(0, 0, 2);
            /* A-side edit layer */
            editLayer.display();
            /*Translate text a bit higher. */
            app.translate(0, -5, 0);
            drawText(sideA);
            app.translate(0, 5, 0);

        } else {
            app.translate(0, 0, -2);
            app.rotateY(app.PI);
            /* B-side edit layer */
            editLayer.display();
            app.translate(0, -5, 0);
            drawText(sideB);
            app.translate(0, 5, 0);
        }

        app.popMatrix();
    }

    public void turn() {
        targetAngle += app.PI;
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
        app.fill(TEXT_COLOR);
        float roundAngle = angle % (app.TWO_PI);
        if (roundAngle < app.HALF_PI || roundAngle > (app.HALF_PI + app.PI)) {
            app.textSize(currentTextSizeA);
        } else {
            app.textSize(currentTextSizeB);
        }
        app.text(text, 0, 0, 0);
        if (isEditing && (Math.abs(angle - targetAngle) < 0.05) && (app.millis() / 700 % 2 == 0)) {
            float width = app.textWidth(text);
            app.stroke(127);
            app.strokeWeight(2);
            app.line(width / 2, 20, 0, width / 2, -20, 0);
        }
    }


    public void editText(final char key) {
        if (isEditing && key != app.CODED) {
            if (key == app.BACKSPACE) {
                this.setCurrentSide(this.getCurrentSide().substring(0, Math.max(0, this.getCurrentSide().length() - 1)));
                /* Increase text font */
                if (app.textWidth(this.getCurrentSide() + "—-") < MAX_TEXT_WIDTH) {
                    if (side) {
                        if (currentTextSizeB < TEXT_SIZE) {
                            currentTextSizeB++;
                        }
                    } else {
                        if (currentTextSizeA < TEXT_SIZE) {
                            currentTextSizeA++;
                        }
                    }
                }
            } else if (key == app.ENTER || key == app.RETURN) {
                this.turn();
            } else {
                this.setCurrentSide(this.getCurrentSide() + key);
            }
        }
        if (app.textWidth(this.getCurrentSide()) > MAX_TEXT_WIDTH) {
            if (side) {
                currentTextSizeB--;
            } else {
                currentTextSizeA--;
            }
        }
    }

    public void endEditing() {
        editLayer.deactivate();
        isEditing = false;
    }

    public void startEditing() {
        isEditing = true;
    }

    public void markAsLearned() {
        isLearned = true;
        this.disappear();
    }

    public void markAsNew() {
        isLearned = false;
        this.appear();
    }

    public boolean isLearned() {
        return isLearned;
    }

    public void mouseClicked() {
        editLayer.mouseClicked();
    }

    public CardsCollection getCollection() {
        return collection;
    }

    public void setCollection(final CardsCollection collection) {
        this.collection = collection;
    }

    /* Real card position for camera transform. */
    public float getRealX() {
        return realX;
    }

    public float getRealY() {
        return realY;
    }

    public float getRealZ() {
        return realZ;
    }
}
