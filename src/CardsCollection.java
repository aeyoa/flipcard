import apple.laf.JRSUIConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arsenykogan on 10/06/14.
 */
public class CardsCollection {

    private final Flipcard app;
    private ArrayList<Card> cards;
    private Card focusCard;
    private int focusIndex;
    private static final long NEW_CARD_DELAY = 130;

    /* Create empty collection of cards. */
    public CardsCollection(final Flipcard pApplet) {
        this.app = pApplet;
        cards = new ArrayList<>();
        focusCard = null;
        focusIndex = -1;
    }

    /* Create collection from JSON file */
    public CardsCollection(final Flipcard app, final CardsCollectionExport export) {
        this.app = app;
        cards = new ArrayList<>();
        int i = 0;
        for (CardsCollectionExport.CardWrapper cardWrapper : export.cardWrappers) {
            final Card newCard = new Card(app, this, cardWrapper.getSideA(), cardWrapper.getSideB());
            for (int j = 0; j < i; j++) {
                newCard.moveRight();
            }
            newCard.endEditing();
            cards.add(newCard);
            i++;
        }
        focusIndex = 0;
        focusCard = cards.get(focusIndex);
        for (Card card : cards) {
            card.appear();
        }
    }

    public void addCard() {

        if (focusCard != null) {
            focusCard.endEditing();
        }

        /* Move all cards on the left by one step to the left. */
        for (int i = 0; i <= focusIndex; i++) {
            cards.get(i).moveLeft();
        }
        /* Create new card and insert it to the cards list. */
        final Card newCard = new Card(app, this);
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

    public void addCard(final Card newCard) {

        if (focusCard != null) {
            focusCard.endEditing();
        }

        /* Move all cards on the left by one step to the left. */
        for (int i = 0; i <= focusIndex; i++) {
            cards.get(i).moveLeft();
        }
        /* Create new card and insert it to the cards list. */
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


    public void removeFocusCard() {
        if (focusCard != null) {
            focusCard.disappear();

            /* Waiting while deleted card is moving down */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(NEW_CARD_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /* Removing focusCard from collection. */
                    cards.remove(focusIndex);

                    if (cards.isEmpty()) {
                        /* Last card in collection */
                        focusCard = null;
                        focusIndex = -1;
                    } else if (focusIndex == cards.size()) {
                        /* If focusCard is on the right */
                        focusCard = cards.get(--focusIndex);
                        for (Card card : cards) {
                            card.moveRight();
                        }
                    } else {
                        focusCard = cards.get(focusIndex);
                        for (int i = focusIndex; i < cards.size(); i++) {
                            cards.get(i).moveLeft();
                        }
                    }

                }
            }).start();
        }
    }

    public void display() {
        for (Card card : cards) {
            card.display();
        }
    }

    public void moveFocusLeft() {
        /* If focusCard is not first. */
        if (focusIndex > 0) {
            focusCard.endEditing();
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
        if (focusCard != null) {
            focusCard.turn();
        }
    }


    public void editFocusCard(final char key) {
        if (focusCard != null) {
            focusCard.editText(key);
        }
    }

    public void moveToFirstCard() {
        if (focusCard != null && focusIndex == cards.size() - 1) {
            for (Card card : cards) {
                for (int i = 0; i < cards.size() - 1; i++) {
                    card.moveRight();
                }
            }
            focusCard = cards.get(0);
            focusIndex = 0;
        }
    }

    public void markFocusCardAsLearned() {
        focusCard.markAsLearned();
        /* Change the collection of learned card to learned-collection */
        focusCard.setCollection(app.getLearnedCardsCollection());
        app.getLearnedCardsCollection().addCard(focusCard);
        this.removeFocusCard();
    }

    public void markFocusCardAsNew() {
        focusCard.markAsNew();
        /* Change the collection of new card to main collection */
        focusCard.setCollection(app.getCardsCollection());
        app.getCardsCollection().addCard(focusCard);
        this.removeFocusCard();
    }

    public void shuffleCards() {
        if (cards.size() > 1) {
            final ArrayList<Integer> positions = new ArrayList<>();
            for (int i = 0; i < cards.size(); i++) {
                positions.add(i);
            }
            Collections.shuffle(positions);
            final ArrayList<Card> shuffledCards = new ArrayList<>();
            for (int i = 0; i < positions.size(); i++) {
                shuffledCards.add(cards.get(positions.get(i)));
                int shift = i - positions.get(i);
                for (int j = 0; j < Math.abs(shift); j++) {
                     if (shift > 0) {
                         shuffledCards.get(i).moveRight();
                     } else {
                         shuffledCards.get(i).moveLeft();
                     }
                }
            }
            cards = shuffledCards;
            focusCard = cards.get(focusIndex);
        }
    }

    public void flipAllToA() {
        boolean wasFlipped = false;
        for (Card card : cards) {
            wasFlipped = card.flipToA() || wasFlipped;
        }
        if (!wasFlipped) {
            for (Card card : cards) {
                card.turn();
            }
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean isLastCard() {
        /* If focusCard is not last. */
        if (focusIndex == cards.size() - 1) {
            return true;
        }
        return false;
    }

    public int getCount() {
        return cards.size();
    }

    public void endEditingOfFocusCard() {
        if (focusCard != null) {
            focusCard.endEditing();
        }
    }


    public void mouseClicked() {
        if (focusCard != null) {
            focusCard.mouseClicked();
        }
    }

    public Card getFocusCard() {
        return focusCard;
    }

    /* Wrapper to cards collection for JSON export. */
    public CardsCollectionExport getCardsCollectionExport() {
        return new CardsCollectionExport(cards);
    }

    public class CardsCollectionExport {

        private CardWrapper[] cardWrappers;

        public CardsCollectionExport(final List<Card> cards) {
            cardWrappers = new CardWrapper[cards.size()];
            for (int i = 0; i < cards.size(); i++) {
                cardWrappers[i] = new CardWrapper(cards.get(i).getSideA(), cards.get(i).getSideB());
            }
        }

        private class CardWrapper {

            private final String sideA;
            private final String sideB;

            private CardWrapper(final String sideB, final String sideA) {
                this.sideB = sideB;
                this.sideA = sideA;
            }

            public String getSideA() {
                return sideA;
            }

            public String getSideB() {
                return sideB;
            }
        }
    }
}
