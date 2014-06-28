/**
 * Created by arsenykogan on 28/06/14.
 */
public class SavedPopUp {

    private static int height = 10;
    private EasingValue width = new EasingValue(0.07, 0.5);
    private final int color;
    private boolean active;


    private final Flipcard app;

    public SavedPopUp(final Flipcard app) {
        this.app = app;
        width.setTarget(0);
        active = false;
        color = app.color(173, 188, 253);
    }


    public void display() {
        width.update();
        if (active) {
            app.rectMode(app.CORNER);
            app.noStroke();
            app.fill(color);
            app.rect(0, 0, (float) width.getCurrentValue(), height);
        }
    }

    public void show() {
        active = true;
        width.setTarget(app.width);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                active = false;
            }
        }).start();
    }

}


