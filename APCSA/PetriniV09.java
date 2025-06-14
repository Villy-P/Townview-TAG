import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetriniV09 {
    private Scanner scanner = new Scanner(System.in);

    private Card[] deck = new Card[52];
    private Card[] playerDeck = new Card[52];
    private String[] suits = new String[] {"\3", "\4", "\5", "\6"};
    private int[] values = new int[] {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    private String[] names = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    private int points = 0;

    public int randomNumber(double min, double max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public int getMenuChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String userInput = this.scanner.nextLine();
            Pattern pattern = Pattern.compile("[+]?[0-9]*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) {
                int value = Integer.parseInt(userInput);
                if (value < 0 || value > 5)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    public Card[] remove(int index) {
        Card[] newList = new Card[this.deck.length - 1];
        for (int i = 0, j =0; i < this.deck.length; i++) {
            if (i == index)
                continue;
            newList[j++] = this.deck[i];
        }
        return newList;
    }

    public void populateDeck() {
        for (int i = 0; i < 52; i++)
            deck[i] = new Card(this.suits[(int)(i % 4)], this.values[(int)(i / 4)], this.names[(int)(i / 4)]);
    }

    public void mainMenu() {
        this.populateDeck();
        mainloop:
        while (true) {
            System.out.println("\n");
            System.out.println("MENU:");
            System.out.println("1) Display the player's hand and points");
            System.out.println("2) Display the deck");
            System.out.println("3) Pull a card from the deck");
            System.out.println("4) Reset");
            System.out.println("5) Exit");
            int input = this.getMenuChoice("Pick an option (1 - 4) ");
            System.out.println("");
            switch (input) {
                case 1:
                    for (Card c : this.playerDeck)
                        if (c != null)
                            System.out.print(c.getName() + c.getSuit() + "\t");
                    System.out.println("Total Points : " + this.points);
                    break;
                case 2:
                    for (Card c : this.deck) 
                        System.out.print(c.getName() + c.getSuit() + " ");
                    break;
                case 3:
                    int randomValue = this.randomNumber(0, this.deck.length - 1);
                    Card card = this.deck[randomValue];
                    System.out.println("Card Pulled : " + card.getName() + card.getSuit());
                    this.deck = this.remove(randomValue);
                    this.points += card.getPointValue();
                    playerDeck[51 - this.deck.length] = card;
                    if (this.deck.length == 0) {
                        System.out.println("You have drawn all cards.");
                        break mainloop;
                    }
                    break;
                case 4:
                    this.deck = new Card[52];
                    this.populateDeck();
                    this.points = 0;
                    break;
                case 5:
                    break mainloop;
            }
        }
    }

    public static void main(String[] args) {
        PetriniV09 main = new PetriniV09();
        main.mainMenu();
    }
}

class Card {
    private String suit;
    private int pointValue;
    private String name;

    public Card(String suit, int pointValue, String name) {
        this.suit = suit;
        this.pointValue = pointValue;
        this.name = name;
    }

    public String getSuit() {
        return this.suit;
    }

    public int getPointValue() {
        return this.pointValue;
    }

    public String getName() {
        return this.name;
    }
}