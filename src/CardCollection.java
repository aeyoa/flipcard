import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arsenykogan on 23/05/14.
 */
public class CardCollection<FlipCard> implements Iterable<FlipCard>{

    private final List<FlipCard> cards;

    public CardCollection() {
        this.cards = new ArrayList<FlipCard>();
    }

    public void addCard(final FlipCard card) {
        cards.add(card);
    }

    public void removeCard(final FlipCard card) {
        cards.remove(card);
    }

    public void flipAll() {

    }

    @Override
    public Iterator<FlipCard> iterator() {
        return cards.iterator();
    }
}
