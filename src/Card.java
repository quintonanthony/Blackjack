public class Card {
    private int val;
    private int suit;

    public Card(int suit , int val) {
        this.suit = suit;
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public int getSuit() {
        return suit;
    }

    public String toString() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        return values[this.val-2] + " " + suits[this.suit];
    }



}
