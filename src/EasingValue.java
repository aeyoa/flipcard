/**
 * Created by arsenykogan on 15/06/14.
 */
public class EasingValue {

    private double easing;
    private double delta;
    private double target;
    private double currentValue;

    public EasingValue(final double easing, final double delta) {
        this.easing = easing;
        this.delta = delta;
    }

    public void setTarget(final double target) {
        this.target = target;
    }

    public void setEasing(final double easing) {
        this.easing = easing;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void update() {
        if (Math.abs(target - currentValue) > delta) {
            currentValue += (target - currentValue) * easing;
        } else {
            currentValue = target;
        }
    }
}
