import java.util.ArrayList;

public class PetriniV14 {
    public static ArrayList<Card> deck = new ArrayList<>();

    public static String[] suits  = new String[] {"\3", "\4", "\5", "\6"};
    public static int[]    values = new int[]    {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    public static String[] names  = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static void create() {
        for (int i = 0; i < 52; i++)
            deck.add(new Card(suits[(int)(i % 4)], values[(int)(i / 4)], names[(int)(i / 4)]));
    }

    public static void display() {
        for (Card c : deck)
            System.out.print(c + " ");
        System.out.println();
    }

    public static void shuffle() {
        for (int i = 0; i < 1000000; i++) {
            int index1 = randomNumber(0, deck.size() - 1);
            int index2 = randomNumber(0, deck.size() - 1);
            deck.set(index1, deck.set(index2, deck.get(index1)));
        }
    }

    public static void sort() {
        for (int i = 0; i < deck.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < deck.size(); j++)
                if (deck.get(j).getSuit().compareTo(deck.get(min).getSuit()) < 0)
                    min = j;
            deck.set(min, deck.set(i, deck.get(min)));
        }
    }

    public static void main(String[] tacos) {
        create();
        System.out.println("Original Deck");
        display();
        shuffle();
        System.out.println("Shuffled Deck");
        display();
        sort();
        System.out.println("Sorted Deck");
        display();
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