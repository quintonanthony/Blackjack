import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private ArrayList<Card> cards;

    public  Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        for (int deck = 0; deck < 6; deck++) {
            for (int suit = 0; suit < 4; suit++) {
                for (int val = 2; val <= 14; val++) {
                    cards.add(new Card(suit, val));
                }
            }
        }
    }

        public void shuffle() {
            Collections.shuffle(cards);
        }

        public Card getCard() {
            if(!cards.isEmpty()){
                return cards.remove(cards.size()-1);
            }
            return null;
        }

}

