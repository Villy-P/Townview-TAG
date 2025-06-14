import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PetriniV18 {
    public static Scanner scanner = new Scanner(System.in);

    public static double getMenuChoice(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();
            Pattern pattern = Pattern.compile("[+$]?[0-9]*.?([0-9]*)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) {
                if (userInput.charAt(0) == '$')
                    userInput = userInput.substring(1);
                double value = Double.parseDouble(userInput);
                if (value < min || value > max)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    public static void main(String[] tacos) {
        while (true) {
            double choice = getMenuChoice("How much money?\t", 0, Double.MAX_VALUE);
            int bills = (int) choice;
            int coins = (int) ((choice - bills) * 100);
            System.out.println("\nBills\n----------");
            System.out.printf("%-15s%d\n", "Hundreds:", bills / 100);
            bills %= 100;
            System.out.printf("%-15s%d\n", "Fifties:", bills / 50);
            bills %= 50;
            System.out.printf("%-15s%d\n", "Twenties:", bills / 20);
            bills %= 20;
            System.out.printf("%-15s%d\n", "Tens:", bills / 10);
            bills %= 10;
            System.out.printf("%-15s%d\n", "Fives:", bills / 5);
            bills %= 5;
            System.out.printf("%-15s%d\n", "Ones:", bills);
            System.out.println("\nCoins\n----------");
            System.out.printf("%-15s%d\n", "Quarters:", coins / 25);
            coins %= 25;
            System.out.printf("%-15s%d\n", "Dimes:", coins / 10);
            coins %= 10;
            System.out.printf("%-15s%d\n", "Nickels:", coins / 5);
            coins %= 5;
            System.out.printf("%-15s%d\n\n\n", "Pennies:", coins);
        }
    }
}