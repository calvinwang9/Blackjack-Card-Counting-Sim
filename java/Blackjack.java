import java.util.*;
import java.io.PrintStream;
import java.io.OutputStream;

public class Blackjack {

    public static ArrayList<Card> createDeck() {
        // List<String> card_type = Arrays.asList("♠", "♥", "♣", "♦");
        final List<String> card_type = Arrays.asList("S", "H", "C", "D");
        final List<String> card_value = Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");
        final ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(card_type.get(i), card_value.get(j)));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public static String playHand(final Hand playerHand, final Hand dealerHand, final ArrayList<Card> deck, int bet,
            int running_count) {
        // player turn
        if (playerHand.getValue() == 21) {
            System.out.println("dealer\t" + dealerHand.getCards().get(0).getNum()
                    + dealerHand.getCards().get(0).getSuit() + " " + dealerHand.getCards().get(1).getNum()
                    + dealerHand.getCards().get(1).getSuit() + " = " + dealerHand.getValue());
            if (dealerHand.getValue() == 21) {
                return "push";
            } else {
                return "player blackjack";
            }
        } else {
            System.out.println("\nplayer turn");
            String action = Strategy.playerStrategy(playerHand, dealerHand.getCards().get(0));
            while (action != "stand") {
                final Card card = deck.remove(0);
                playerHand.addCard(card);
                running_count += Strategy.hiLoCount(card);

                if (action == "double") {
                    System.out.println(
                            "player double " + card.getNum() + card.getSuit() + "\ttotal = " + playerHand.getValue());
                    bet += bet;
                } else {
                    System.out.println(
                            "player hit " + card.getNum() + card.getSuit() + "\ttotal = " + playerHand.getValue());
                }

                if (playerHand.getBust() == true) {
                    return "player bust";
                }
                if (action == "double") {
                    break;
                } else {
                    action = Strategy.playerStrategy(playerHand, dealerHand.getCards().get(0));
                }
            }
        }

        // dealer turn
        System.out.println("player stands\n\ndealer turn");
        System.out.println("dealer\t" + dealerHand.getCards().get(0).getNum() + dealerHand.getCards().get(0).getSuit()
                + " " + dealerHand.getCards().get(1).getNum() + dealerHand.getCards().get(1).getSuit() + " = "
                + dealerHand.getValue());

        if (dealerHand.getValue() == 21) {
            return "dealer blackjack";
        }
        while (dealerHand.getValue() < 17) {
            final Card card = deck.remove(0);
            dealerHand.addCard(card);
            running_count += Strategy.hiLoCount(card);
            System.out.println("dealer hit " + card.getNum() + card.getSuit() + "\ttotal = " + dealerHand.getValue());
            if (dealerHand.getBust() == true) {
                return "dealer bust";
            }
        }

        if (playerHand.getValue() > dealerHand.getValue()) {
            return "player win";
        } else if (playerHand.getValue() < dealerHand.getValue()) {
            return "dealer win";
        } else {
            return "push";
        }
    }

    public static void main(final String[] args) {
        final PrintStream yes_display = System.out;
        final PrintStream no_display = new PrintStream(new OutputStream() {
            public void write(final int b) {
                // NO-OP
            }
        });
        // toggle blackjack text display
        System.setOut(no_display);

        // initialised variables
        int player_score = 0;
        int dealer_score = 0;
        int hands_played = 0;
        int cash = 0;
        // input parameters (change these)
        final int num_rounds = 1000000;
        final int num_decks = 1;
        final int min_bet = 10;

        for (int i = 0; i < num_rounds; i++) {
            // initialise shuffled deck
            final ArrayList<Card> deck = createDeck();

            // play deck until cut card at 10 cards
            int hand = 0;
            int running_count = 0;
            while (deck.size() > 12) {
                hand++;
                hands_played++;
                System.out.println("--------Hand " + hand + "--------");

                // place bet - betting logic
                int bet;
                if (running_count > 5) {
                    bet = running_count * min_bet * 10;
                } else {
                    bet = min_bet;
                }
                System.out.println("count = " + running_count);
                System.out.println("bet = " + bet);

                // deal inital cards
                final Hand playerHand = new Hand();
                final Hand dealerHand = new Hand();
                playerHand.addCard(deck.remove(0));
                dealerHand.addCard(deck.remove(0));
                playerHand.addCard(deck.remove(0));
                dealerHand.addCard(deck.remove(0));

                // update card count -> change this implementation so hand isnt counting
                running_count += playerHand.count();
                running_count += dealerHand.count();

                System.out.println("dealer\t" + dealerHand.getCards().get(0).getNum()
                        + dealerHand.getCards().get(0).getSuit() + " ?  = ?");
                System.out.println("player\t" + playerHand.getCards().get(0).getNum()
                        + playerHand.getCards().get(0).getSuit() + " " + playerHand.getCards().get(1).getNum()
                        + playerHand.getCards().get(1).getSuit() + " = " + playerHand.getValue());

                final String result = playHand(playerHand, dealerHand, deck, bet, running_count);
                System.out.println(result);
                switch (result) {
                    case "player blackjack":
                        cash += bet * 1.5;
                        player_score++;
                        break;
                    case "player bust":
                        cash -= bet;
                        dealer_score++;
                        break;
                    case "player win":
                        cash += bet;
                        player_score++;
                        break;
                    case "dealer blackjack":
                        cash -= bet;
                        dealer_score++;
                        break;
                    case "dealer bust":
                        cash += bet;
                        player_score++;
                        break;
                    case "dealer win":
                        cash -= bet;
                        dealer_score++;
                        break;
                    case "push":
                        break;
                }
            }
        }

        // display results
        final double player_percent = (player_score * 1.0) / (hands_played * 1.0) * 100;
        final double dealer_percent = (dealer_score * 1.0) / (hands_played * 1.0) * 100;
        final double tie_percent = (hands_played - player_score - dealer_score) * 1.0 / (hands_played * 1.0) * 100;

        System.setOut(yes_display);
        System.out.println("--------Results--------");
        System.out.println("hands played = " + hands_played);
        System.out.println("player wins " + player_percent + "% of the time");
        System.out.println("dealer wins " + dealer_percent + "% of the time");
        System.out.println("tie occurs " + tie_percent + "% of the time");
        System.out.println("profit/loss = " + cash);
        
    }
}