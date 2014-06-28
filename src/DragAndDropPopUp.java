import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by arsenykogan on 26/06/14.
 */
public class DragAndDropPopUp {

    private final PApplet app;

    private static final int strokeWidth = 10;
    private final int color;
    private boolean active;

    public DragAndDropPopUp(final Flipcard app) {
        this.app = app;
        this.hide();
        color = app.color(173, 188, 253);
    }

    public void display() {
        if (active) {
            app.noFill();
            app.stroke(color);
            app.strokeWeight(strokeWidth);
            app.rectMode(PConstants.CORNERS);
            app.rect(0, 0, app.width, app.height);
        }

    }

    public void hide() {
        active = false;
    }

    public void show() {
        active = true;
    }
}
