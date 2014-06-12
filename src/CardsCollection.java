import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by arsenykogan on 10/06/14.
 */
public class CardsCollection {

    private final PApplet pApplet;
    private final ArrayList<Card> cards;
    private Card focusCard;
    private int focusIndex;
    private static final long NEW_CARD_DELAY = 130;

    /* Create empty collection of cards. */
    public CardsCollection(final PApplet pApplet) {
        this.pApplet = pApplet;
        cards = new ArrayList<>();
        focusCard = null;
        focusIndex = -1;
    }

    /* TODO: add language switching (java.util.Locale) */
    public void addCard() {

        if (focusCard != null) {
            focusCard.endEditing();
        }

        /* Move all cards on the left by one step to the left. */
        for (int i = 0; i <= focusIndex; i++) {
            cards.get(i).moveLeft();
        }
        /* Create new card and insert it to the cards list. */
        final Card newCard = new Card(pApplet);
        cards.add(focusIndex + 1, newCard);

        /* Waiting while cards moving to the left */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long sleepTime = (cards.size() < 2) ? 0 : NEW_CARD_DELAY;
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /* Increase focus index and set focusCard. */
                newCard.appear();
                focusIndex++;
                focusCard = cards.get(focusIndex);
            }
        }).start();
    }

    /* TODO: check out culling */
    public void display() {
        for (Card card : cards) {
            card.display();
        }
    }

    public void moveFocusLeft() {
        /* If focusCard is not first. */
        focusCard.endEditing();
        if (focusIndex > 0) {
            for (Card card : cards) {
                card.moveRight();
            }
            focusCard = cards.get(--focusIndex);
        }

    }

    public void moveFocusRight() {
        /* If focusCard is not last. */
        if (focusIndex < cards.size() - 1) {
            focusCard.endEditing();
            for (Card card : cards) {
                card.moveLeft();
            }
            focusCard = cards.get(++focusIndex);
        }
    }

    public void flipFocusCard() {
        focusCard.turn();
    }

    public void editFocusCard(final char key) {
        focusCard.editText(key);
    }
}
