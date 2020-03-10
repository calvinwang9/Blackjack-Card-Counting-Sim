
public class Strategy {

    // hi-lo count
    public static int hiLoCount(Card card) {
        if (card.getValue() == 10 || card.getValue() == 11) {return -1;}
        else if (card.getValue() < 7) {return 1;}
        else {return 0;}
    }

    // single deck blackjack dealer stands on soft 17
    public static String playerStrategy(Hand playerHand, Card dealerCard) {
        if (playerHand.getSoft() == 0) {
            // hard strategy
            if (playerHand.getValue() < 12) {
                if (playerHand.getValue() == 11) {return "double";}
                if (playerHand.getValue() == 10 && dealerCard.getValue() < 10) {return "double";}
                if (playerHand.getValue() == 9 && dealerCard.getValue() < 7) {return "double";}
                if (playerHand.getValue() == 8 && (dealerCard.getValue() == 5 || dealerCard.getValue() == 6)) {return "double";}
                return "hit";
            } else if (playerHand.getValue() == 12) {
                if (dealerCard.getValue() > 3 && dealerCard.getValue() < 7) {return "stand";}
                return "hit";
            } else if (playerHand.getValue() < 17) {
                if (dealerCard.getValue() > 6) {return "hit";}
                return "stand";
            } else {
                return "stand";
            }
        } else {
            // soft strategy
            if (playerHand.getValue() < 18) {
                if (playerHand.getValue() == 17 && dealerCard.getValue() < 7) {return "double";}
                if (dealerCard.getValue() > 3 && dealerCard.getValue() < 7) {return "double";}
                return "hit";
            } else {
                if (playerHand.getValue() == 18) {
                    if (dealerCard.getValue() == 9 || dealerCard.getValue() == 10) {return "hit";}
                    if (dealerCard.getValue() > 2 && dealerCard.getValue() < 7) {return "double";}
                    return "stand";
                } else {
                    if (playerHand.getValue() == 19 && dealerCard.getValue() == 6) {return "double";}
                    return "stand";
                }
            }
        }
    }
}