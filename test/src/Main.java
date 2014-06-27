import processing.core.PApplet;

/**
 * Created by arsenykogan on 12/06/14.
 */
public class Main extends PApplet {

    private boolean centered;

    @Override
    public void setup() {
        size(400, 400, P3D);
        rectMode(CENTER);
        image(loadImage("moonwalk.jpg"), 0, 0);
        textFont(createFont("Helvetica-Bold", 30));
    }

    @Override
    public void draw() {
        text("hello", 100, 100);
        fill(127);

    }

}
