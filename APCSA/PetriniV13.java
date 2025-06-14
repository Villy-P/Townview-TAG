import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class PetriniV13 {
    public static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Card> fullDeck = new ArrayList<>();
    public static ArrayList<Card> deck     = new ArrayList<>();
    public static ArrayList<Card> hand     = new ArrayList<>();

    public static String[] suits  = new String[] {"\3", "\4", "\5", "\6"};
    public static int[]    values = new int[]    {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    public static String[] names  = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public static int getMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String userInput = PetriniV13.scanner.nextLine();
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

    public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static void populateFullDeck() {
        for (int i = 0; i < 52; i++)
            fullDeck.add(new Card(suits[(int)(i % 4)], values[(int)(i / 4)], names[(int)(i / 4)]));
    }

    public static int getTotalValue() {
        int totalValue = 0;
        for (Card c : hand)
            totalValue += c.getPointValue();
        return totalValue;
    }

    public static void displayCards() {
        System.out.printf("The deck has %d cards in it :\n", deck.size());
        for (Card c : deck)
            System.out.print(c + " ");
        System.out.print("\n");
        System.out.printf("The player has %d cards :\n", hand.size());
        for (Card c : hand)
            System.out.print(c + " ");
        System.out.printf("\nTotal Value = %d\n\n", getTotalValue());
    }

    public static void resetDeck() {
        int input = getMenuChoice("How many cards do you want in the deck? ", 0, 52);
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        fullDeck = new ArrayList<>();
        populateFullDeck();
        for (int i = 0; i < input; i++) {
            int index = randomNumber(0, fullDeck.size() - 1);
            deck.add(fullDeck.get(index));
            fullDeck.remove(index);
        }
    }

    public static void sortDeck() {
        for (int i = 0; i < deck.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < deck.size(); j++)
                if (deck.get(j).getPointValue() < deck.get(min).getPointValue())
                    min = j;
            deck.set(min, deck.set(i, deck.get(min)));
        }
    }

    public static void deleteSuit() {
        System.out.println("\n");
        System.out.println("1) Hearts \3");
        System.out.println("2) Diamonds \4");
        System.out.println("3) Clubs \5");
        System.out.println("4) Spades \6");
        int input = getMenuChoice("Which suit do you want to delete?\t", 1, 4);
        for (int i = 0; i < deck.size(); i++)
            if (deck.get(i).getSuit().equals(suits[input - 1]))
                deck.remove(deck.get(i));
    }

    public static void shuffleDeck() {
        // No Collections :(
        for (int i = 0; i < 1000000; i++) {
            int index1 = randomNumber(0, deck.size() - 1);
            int index2 = randomNumber(0, deck.size() - 1);
            deck.set(index1, deck.set(index2, deck.get(index1)));
        }
    }

    public static void pullCard() {
        if (deck.size() == 0) {
            System.out.println("There are no cards left in the deck.");
            return;
        }
        Card card = deck.get(randomNumber(0, deck.size() - 1));
        deck.remove(card);
        hand.add(card);
        System.out.println("Pulled : " + card);
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getPointValue() > card.getPointValue()) {
                deck.add(i, card);
                break;
            }
        }
    }

    public static void main(String[] tacos) {
        populateFullDeck();
        while (true) {
            System.out.println("\n\nMENU:");
            System.out.println("1) Display the cards");
            System.out.println("2) Reset the deck");
            System.out.println("3) Sort the deck");
            System.out.println("4) Delete a suit");
            System.out.println("5) Shuffle the deck");
            System.out.println("6) Pull a card");
            System.out.println("7) Exit");
            switch (getMenuChoice("Pick an option  ", 1, 7)) {
                case 1 -> displayCards();
                case 2 -> resetDeck();
                case 3 -> sortDeck();
                case 4 -> deleteSuit();
                case 5 -> shuffleDeck();
                case 6 -> pullCard();
                case 7 -> System.exit(0);
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

    @Override
    public String toString() {
        return this.name + this.suit;
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