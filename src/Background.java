import processing.core.PImage;

/**
 * Created by arsenykogan on 26/06/14.
 */
public class Background {

    private final String filename = "background.png";

    private final Flipcard app;
    private final PImage background;
    private final EasingValue alpha = new EasingValue(0.1, 0.1);

    public Background(final Flipcard app) {
        this.app = app;
        background = app.loadImage(filename);
        alpha.setTarget(255);
    }

    public void display() {
        alpha.update();
        app.tint(255, (float) alpha.getCurrentValue());
        app.imageMode(app.CORNER);
        app.image(background, 0, -4);
        app.noTint();
    }

    public void hide() {
        alpha.setTarget(0);
    }

    public void show() {
        alpha.setTarget(255);
    }
}
