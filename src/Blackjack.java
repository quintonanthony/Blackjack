import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;
    private int playerChips;
    private String playerName;
    private int playerBet;



    Scanner kb;

    public Blackjack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
        playerChips = 100; // Initial number of chips for the player
        deck.shuffle(); // Shuffle the deck after initialization
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
            game.setPlayerNameAndBet();
            game.run();

    }

        private void setPlayerName() {
        System.out.println("Enter your name: ");
        playerName = kb.nextLine();
        System.out.println("Welcome, to Q's Casino " + playerName + ". Today we will be playing Blackjack!");
        System.out.println("Each guest is given 100 chips to bet with");
        System.out.println("Have fun and please... Gamble responsibly");
        System.out.println();
    }

    private void getPlayerBet() {
        while (true) {
            System.out.println("Current chips: " + playerChips);
            System.out.println("How many chips would you like to bet?");
            try {
                int bet = Integer.parseInt(kb.nextLine());

                if (bet < 1 || bet > playerChips) {
                    System.out.println("Invalid bet. Please enter a value between 1 and " + playerChips);
                } else {
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void setPlayerNameAndBet() {
        setPlayerName();
        getPlayerBet();
    }

    private void run() {
        while (true) {
            if (playerChips <= 0) {
                System.out.println("You're out of chips. Game over!");
                break;
            }

            dealCards();
            System.out.println("Dealer's hand:\t" + dealer.get(0) + " (Hidden)");
            System.out.println(playerName + "'s hand:\t" + getPlayerHand());
            System.out.println(playerName + "'s hand sum: " + calculateHandValue(player));

            if (calculateHandValue(player) == 21) {
                System.out.println("Blackjack! You win!");
                playerChips += 15; // Payout for Blackjack (adjust as needed)
            } else {
                playerTurn();
                dealerTurn();
                determineWinner(playerBet);
            }

            System.out.println("\nDo you want to play again? (yes/no)");
            String playAgain = kb.nextLine().toLowerCase();
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private void dealCards() {
        // Reset hands
        player.clear();
        dealer.clear();

        // Deal initial cards
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

    }

    private void playerTurn() {
        while (true) {
            System.out.println("\nWould you like to hit or stand? [h/s]");
            String response = kb.nextLine();

            if (response.toLowerCase().equals("hit") || response.toLowerCase().equals("h")) {
                player.add(deck.getCard());
                System.out.println(playerName + "'s hand:\t" + getPlayerHand());
                System.out.println(playerName+ "'s' hand sum: " + calculateHandValue(player));

                // Check for bust after hitting
                if (calculateHandValue(player) > 21) {
                    System.out.println("Bust! You lose.");
                    playerChips -= 10; // Adjust the chip amount as needed
                    break;
                }

                // Check for Blackjack after hitting
                if (calculateHandValue(player) == 21) {
                    System.out.println("Blackjack! You win!");
                    playerChips += 15; // Payout for Blackjack (adjust as needed)
                    break;
                }
            } else if (response.toLowerCase().equals("stand") || response.toLowerCase().equals("s")) {
                break;
            }
        }
    }

    private void dealerTurn() {
        // Reveal the hidden card
        System.out.println("\nDealer's hand:\t" + getDealerHand());

        // Dealer hits until hand value is at least 17
        while (calculateHandValue(dealer) < 17) {
            dealer.add(deck.getCard());
            System.out.println("Dealer: Hits");
            System.out.println("Dealer's hand:\t" + getDealerHand());
        }

        System.out.println("Dealer's hand total: " + calculateHandValue(dealer));
    }

    private void determineWinner(int bet) {
        int playerValue = calculateHandValue(player);
        int dealerValue = calculateHandValue(dealer);

        System.out.println("\nFinal Hands:");
        System.out.println("Dealer's hand:\t" + getDealerHand());
        System.out.println(playerName + "'s hand:\t" + getPlayerHand());
        System.out.println("Dealer's hand total: " + calculateHandValue(dealer));
        System.out.println(playerName + "'s hand sum: " + calculateHandValue(player));

        System.out.println();



        if (playerValue > 21 || (dealerValue <= 21 && dealerValue >= playerValue)) {
            System.out.println("Dealer wins. You lose :(");
            playerChips -= bet;
        } else if (playerValue == dealerValue) {
            System.out.println("It's a push! No chips are lost or gained.");
        } else {
            playerChips += bet;
        }
        System.out.println(playerName + "'s total chips: " + playerChips);
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            switch (card.getVal()) {
                case 11, 12, 13 -> value += 10;
                case 14 -> {
                    value += 11;
                    numAces++;
                }
                default -> value += card.getVal();
            }
        }

        // Adjust for aces
        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }


    private String getPlayerHand() {
        StringBuilder handString = new StringBuilder();
        for (Card card : player) {
            handString.append(card).append(" ");
        }
        return handString.toString();
    }

    private String getDealerHand() {
        StringBuilder handString = new StringBuilder();
        for (int i = 0; i < dealer.size(); i++) {
            if (i == 0) {
                handString.append(dealer.get(i)).append(" (Hidden) ");
            } else {
                handString.append(dealer.get(i)).append(" ");
            }
        }
        return handString.toString();
    }
}