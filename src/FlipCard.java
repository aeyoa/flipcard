/**
 * Created by arsenykogan on 23/05/14.
 */
public class FlipCard {

    private String sideA;
    private String sideB;
    /* false stands for side A
    * true stands for side B. */
    private boolean currentSide;

    public FlipCard(final String sideA, final String sideB) {
        currentSide = false;
        this.sideA = sideA;
        this.sideB = sideB;
    }

    public String getSideA() {
        return sideA;
    }

    public String getSideB() {
        return sideB;
    }

    public void setSideA(final String sideA) {
        this.sideA = sideA;
    }

    public void setSideB(final String sideB) {
        this.sideB = sideB;
    }

    public void flip() {
        currentSide = !currentSide;
    }

    public String getCurrentSideWord() {
        return (currentSide) ? sideB : sideA;
    }

    public void flipToA() {
        currentSide = false;
    }
}
