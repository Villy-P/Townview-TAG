import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetriniV10 {
    public Scanner scanner = new Scanner(System.in);

    public Card[] deck = new Card[5];
    public static String[] suits = new String[] {"\3", "\4", "\5", "\6"};
    public static int[] values = new int[] {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    public static String[] names = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public static int randomNumber(int min, int max) {
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

    public void populateDeck() {
        for (int i = 0; i < 5; i++)
            this.deck[i] = new Card();
    }

    public void mainMenu() {
        this.populateDeck();
        mainloop:
        while (true) {
            System.out.println("\n\n");
            System.out.println("MENU:");
            System.out.println("1) Display the cards");
            System.out.println("2) Put the cards in order");
            System.out.println("3) Are they in order?");
            System.out.println("4) Add a new card");
            System.out.println("5) Exit");
            int input = this.getMenuChoice("Pick an option (1 - 5) ");
            System.out.println("");
            switch (input) {
                case 1:
                    for (Card c : this.deck)
                        System.out.printf("%s%s ", c.getName(), c.getSuit());
                    break;
                case 2:
                    for (int i = 0; i < this.deck.length - 1; i++) {
                        int min = i;
                        for (int j = i + 1; j < this.deck.length; j++)
                            if (this.deck[j].getPointValue() < this.deck[min].getPointValue())
                                min = j;
                        Card temp = this.deck[min];
                        this.deck[min] = this.deck[i];
                        this.deck[i] = temp;
                        System.out.println(String.format("%s%s is the smallest", this.deck[i].getName(), this.deck[i].getSuit()));
                        for (Card c : this.deck)
                            System.out.printf("%s%s ", c.getName(), c.getSuit());
                        System.out.print("\n");
                    }
                    break;
                case 3:
                    if (this.isInOrder()) {
                        System.out.print("The cards are in order: ");
                        for (Card c : this.deck)
                            System.out.printf("%s%s ", c.getName(), c.getSuit());
                        System.out.print("\n");
                    } else {
                        System.out.print("The cards are out of order: ");
                        for (Card c : this.deck)
                            System.out.printf("%s%s ", c.getName(), c.getSuit());
                        System.out.print("\n");
                    }
                    break;
                case 4:
                    Card card = new Card();
                    if (!this.isInOrder())
                        this.insertAtBeginning(card);
                    else 
                        this.insertInOrder(card);
                    break;
                case 5:
                    break mainloop;
            }
        }
    }

    public boolean isInOrder() {
        for (int i = 0; i < this.deck.length; i++)
            if (i != 0 && this.deck[i].getPointValue() < this.deck[i - 1].getPointValue())
                return false;
        return true;
    }

    public void insertAtBeginning(Card c) {
        Card[] temp = new Card[this.deck.length + 1];
        temp[0] = c;
        for (int i = 1; i < temp.length; i++)
            temp[i] = this.deck[i - 1];
        this.deck = temp;
    }

    public void insertInOrder(Card c) {
        Card[] temp = new Card[this.deck.length + 1];
        for (int i = 0, j = 0; i < this.deck.length; i++) {
            if (j == 0 && this.deck[i].getPointValue() >= c.getPointValue()) {
                temp[i] = c;
                j++;
                System.out.println(c.getName() + c.getSuit() + " was added at index " + i);
            } 
            temp[i + j] = this.deck[i];
        }
        this.deck = temp;
    }

    public static void main(String[] args) {
        PetriniV10 main = new PetriniV10();
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

    public Card() {
        this.suit = PetriniV10.suits[PetriniV10.randomNumber(0, 3)];
        int index = PetriniV10.randomNumber(0, 12);
        this.pointValue = PetriniV10.values[index];
        this.name = PetriniV10.names[index];
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