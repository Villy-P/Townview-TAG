import java.util.Scanner;
import java.util.regex.*;

public class PetriniV17 {
    public static Scanner scanner = new Scanner(System.in);

    public static void printPrimeFactors(int n) {
        for (int i = 2; i < n - 1; i++) {
            if (n % i == 0) {
                System.out.print(i + " ");
                printPrimeFactors(n / i);
                return;
            } 
        }
        System.out.println(n);
    }

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

    public static void main(String[] args) {
        while (true) {
            System.out.println("");
            printPrimeFactors(getMenuChoice("Enter a number\t", Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }
}