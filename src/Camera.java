/**
 * Created by arsenykogan on 26/06/14.
 */
public class Camera {

    private final EasingValue xPos = new EasingValue(0.1, 0.01);
    private final EasingValue yPos = new EasingValue(0.1, 0.01);
    private final EasingValue zPos = new EasingValue(0.1, 0.01);

    private final EasingValue xLookAt = new EasingValue(0.1, 0.01);
    private final EasingValue yLookAt = new EasingValue(0.1, 0.01);
    private final EasingValue zLookAt = new EasingValue(0.1, 0.01);

    private final Flipcard app;

    public Camera(final Flipcard app) {
        this.app = app;
        this.focusToNew();
    }

    public void transform() {
        xPos.update();
        yPos.update();
        zPos.update();
        xLookAt.update();
        yLookAt.update();
        zLookAt.update();
        app.camera((float) xPos.getCurrentValue(), (float) yPos.getCurrentValue(), (float) xPos.getCurrentValue(),
                (float) xLookAt.getCurrentValue(), (float) yLookAt.getCurrentValue(), (float) zLookAt.getCurrentValue(),
                0, 1, 0);
    }

    public void setToDefault() {
        app.camera(app.width / 2, app.height / 2, (app.height / 2) / app.tan((app.PI * 30 / 180)),
                app.width / 2, app.height / 2, 0,
                0, 1, 0);
    }


    public void focusToLearned() {
//        float realX = app.getLearnedCardsCollection().getFocusCard().getRealX();
//        float realY = app.getLearnedCardsCollection().getFocusCard().getRealY();
//        float realZ = app.getLearnedCardsCollection().getFocusCard().getRealZ();
//        zPos.setTarget(0);
//        yPos.setTarget(0);
//        xPos.setTarget(0);
//        xLookAt.setTarget(realX);
//        yLookAt.setTarget(realY);
//        zLookAt.setTarget(realZ);

        yPos.setTarget(1200);
        yLookAt.setTarget(1200);
    }

    public void focusToNew() {
        xPos.setTarget(app.width / 2);
        yPos.setTarget(app.height / 2);
        zPos.setTarget((app.height / 2) / app.tan((app.PI * 30 / 180)));
        xLookAt.setTarget(app.width / 2);
        yLookAt.setTarget(app.height / 2);
        zLookAt.setTarget(0);
    }
}
