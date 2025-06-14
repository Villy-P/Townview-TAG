import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PetriniV07 {
    public static Scanner scanner = new Scanner(System.in);

    public static int randomNumber(double min, double max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static int rollDie() {
        return randomNumber(1, 6);
    }

    public static int getMenuChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile("[+]?[0-9]*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) {
                int value = Integer.parseInt(userInput);
                if (value < 0)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    public static void main(String[] tacos) {
        int money = getMenuChoice("How many dollars do you have? ");
        int rolls = 0;
        int greatestMoney = 0;
        int greatestTurn = 0;
        while (money > 0) {
            int roll1 = rollDie();
            int roll2 = rollDie();
            if (roll1 + roll2 == 7)
                money += 4;
            else
                money -= 1;
            if (money > greatestMoney) {
                greatestMoney = money;
                greatestTurn = rolls;
            }
            rolls++;
        }
        System.out.println(String.format("You are broke after %d rolls.", rolls));
        System.out.println(String.format("You should have quit after %d rolls when you had %d dollars.", greatestMoney, greatestTurn));
    }
}