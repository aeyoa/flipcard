import processing.core.PApplet;

/**
 * Created by arsenykogan on 12/06/14.
 */
public class Main extends PApplet {

    private boolean centered;

    @Override
    public void setup() {
        size(100, 100);
        rectMode(CENTER);
        rect(250, 250, 200, 200);
        image(loadImage("moonwalk.jpg"), 0, 0);
    }

    @Override
    public void draw() {
        centerWindow();
    }

    void centerWindow() {
        if (frame != null && !centered) {
            frame.setLocation(displayWidth / 2 - width / 2, displayHeight / 2 - height / 2);
            centered = true;
        }
    }
}
