import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PetriniV15 {
	/*Do not alter any existing code*/
	public static ArrayList<Quote> quoteList = new ArrayList<Quote>();
    public static Scanner scanner = new Scanner(System.in);

    public static int getMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();
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

	public static void populateList() {
		quoteList.add(new Quote("Grace Hopper",   "To me programming is more than an important practical art. It is also a gigantic undertaking in the foundations of knowledge."));
		quoteList.add(new Quote("Edsger Dysktra", "The question of whether a computer can think is no more interesting than the question of whether a submarine can swim."));
		quoteList.add(new Quote("Ada Lovelace",   "I never am really satisfied that I understand anything; because, understand it well as I may, my comprehension can only be an infinitesimal fraction of all I want to understand about the many connections and relations which occur to me, how the matter in question was first thought of or arrived atů"));
		quoteList.add(new Quote("Alan Turing",    "One day [people] will take their computers for walks in the park and tell each other, \"My little computer said such a funny thing this morning\"."));
	}

    public static ArrayList<String> getLines(Quote q) {
        String[] splitQuote = q.quote.split(" ");
        ArrayList<String> lines = new ArrayList<>();
        lines.add(splitQuote[0] + " ");
        for (int i = 1; i < splitQuote.length; i++) {
            if (lines.get(lines.size() - 1).length() + splitQuote[i].length() + 1 <= 56)
                lines.set(lines.size() - 1, lines.get(lines.size() - 1) + splitQuote[i] + " ");
            else
                lines.add(splitQuote[i] + " ");
        }
        return lines;
    }

    public static void displayQuote(Quote q) {
        ArrayList<String> lines = getLines(q);
        System.out.println("=".repeat(60));
        for (String s : lines)
            System.out.println(String.format("| %-56s |", s));
        System.out.println(String.format("| %56s |", "- " + q.person));
        System.out.println("=".repeat(60) + "\n");
    }

	public static void main(String args[]) {
		populateList();
		menu();
	}

    public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

	public static void menu() {
		while (true) {
			System.out.println("\n\n1) Display all quotes");
			System.out.println("2) Display a random quote");
			System.out.println("3) Exit");
			switch (getMenuChoice("Select an option\t", 1, 3)) {
                case 1:
                    for (Quote q : quoteList)
                        displayQuote(q);
                    break;
                case 2:
                    displayQuote(quoteList.get(randomNumber(0, quoteList.size() - 1)));
                    break;
                case 3:
                    System.exit(0);
            }
		}
	}
}

class Quote {
    public String person;
    public String quote;

    public Quote(String person, String quote) {
        this.person = person;
        this.quote = quote;
    }
}