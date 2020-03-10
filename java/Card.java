public class Card {
    private String suit;
    private String num;
    private int value;
  
    public Card (String s, String n) {
        suit = s;
        num = n;
        switch(num) {
            case "A": value = 11; break;
            case "2": value = 2; break;
            case "3": value = 3; break;
            case "4": value = 4; break;
            case "5": value = 5; break;
            case "6": value = 6; break;
            case "7": value = 7; break;
            case "8": value = 8; break;
            case "9": value = 9; break;
            case "10": value = 10; break;
            case "J": value = 10; break;
            case "Q": value = 10; break;
            case "K": value = 10; break;
        }
  
    }

    // getters
    public String getSuit() {return suit;}
    public String getNum() {return num;}
    public int getValue() {return value;}
  }