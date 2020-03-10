import java.util.*;

public class Hand {
  private int value = 0;
  private int soft = 0;
  private boolean bust = false;
  private ArrayList<Card> cards = new ArrayList<Card>();

  public void addCard(Card card) {
    cards.add(card);
    calcValue();
  }

  private void calcValue() {
    // sum card values
    int sum = 0;
    for (Card c : cards) {
      sum += c.getValue();
      if (c.getNum() == "A") {soft++;}
    }
    // if we have a soft hand > 21, treat aces as 1
    while (sum > 21 && soft > 0) {
      sum -= 10;
      soft--;
    }
    // if hard hand > 21
    if (sum > 21) {
      bust = true;
    }
    value = sum;
  }

  public int count() {
    int total = 0;
    for (Card c : cards) {
      total += Strategy.hiLoCount(c);
    }
    return total;
  }

  // getters
  public int getValue() {return value;}
  public int getSoft() {return soft;}
  public boolean getBust() {return bust;}
  public ArrayList<Card> getCards() {return cards;}
}
