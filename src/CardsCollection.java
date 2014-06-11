import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by arsenykogan on 10/06/14.
 */
public class CardsCollection {

    private final PApplet pApplet;
    private final ArrayList<Card> cards;
    private Card focusCard;

    /* Create empty collection of cards. */
    public CardsCollection(final PApplet pApplet) {
        this.pApplet = pApplet;
        cards = new ArrayList<>();
    }

    public void addCard() {

    }
}
