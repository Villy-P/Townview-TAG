import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetriniV11 {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Card> deck = new ArrayList<>();
    public static ArrayList<Card> dealer = new ArrayList<>();
    public static String[] suits = new String[] {"\3", "\4", "\5", "\6"};
    public static int[] values = new int[] {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    public static String[] names = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static void populateDeck() {
        for (int i = 0; i < 52; i++)
            dealer.add(new Card(suits[(int)(i % 4)], values[(int)(i / 4)], names[(int)(i / 4)]));
    }

    public static int getMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String userInput = PetriniV11.scanner.nextLine();
            Pattern pattern = Pattern.compile("[+]?[0-9]*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) {
                int value = Integer.parseInt(userInput);
                if (value < min || value > max)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    public static void main(String[] tacos) {
        populateDeck();
        while (true) {
            System.out.println("");
            System.out.println("MENU");
            System.out.println("1) Display the cards");
            System.out.println("2) Reset the deck");
            System.out.println("3) Exit");
            switch (getMenuChoice("Pick an option  ", 1, 3)) {
                case 1:
                    System.out.println("The deck has " + deck.size() + " cards in it :");
                    for (Card c : deck)
                        System.out.print(c.getName() + c.getSuit() + "  ");
                    System.out.println("");
                    break;
                case 2:
                    int input = getMenuChoice("How many cards do you want in the deck? ", 0, 52);
                    deck = new ArrayList<>();
                    dealer = new ArrayList<>();
                    populateDeck();
                    for (int i = 0; i < input; i++) {
                        int numero = randomNumber(0, dealer.size() - 1);
                        deck.add(dealer.get(numero));
                        dealer.remove(dealer.get(numero));
                    }
                    break;
                case 3:
                    System.exit(0);
            }
        }
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