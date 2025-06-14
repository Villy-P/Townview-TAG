import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PetriniV02 {
    private int a;
    private int aLength;
    private int b;
    private int bLength;
    private int c;
    private int cLength;
    private double finalGrade;

    private String firstPrompt  = "How many were right in part ";
    private String secondPrompt = "How many points were available in part ";
    private String invalidText  = "Invalid Input: Number of right is greater than number of points available in part ";

    private Scanner scanner = new Scanner(System.in);

    // Get int Choice from User
    // Copied from one of my old
    // Python project and then 
    // converted to Java
    // (Tweaking a few things too)
    private int getMenuChoice(String prompt) {
        while (true) {
            // Ask user for input
            System.out.print(prompt);
            String userInput = scanner.nextLine();
            // Check if user input is an integer
            // Used regextester.com to get regex pattern
            Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            // If the user input is an integer, check if it is within the range specified
            if (matcher.find()) {
                int value = Integer.parseInt(userInput);
                if (value < 1)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    private void getAValues() {
        this.a       = this.getMenuChoice(this.firstPrompt  + "a?              ");
        this.aLength = this.getMenuChoice(this.secondPrompt + "a?   ");
        if (a > aLength) {
            System.out.println(this.invalidText + "a");
            this.getAValues();
        }
        this.getBValues();
    }

    private void getBValues() {
        this.b       = this.getMenuChoice(this.firstPrompt  + "b?              ");
        this.bLength = this.getMenuChoice(this.secondPrompt + "b?   ");
        if (b > bLength) {
            System.out.println(this.invalidText + "b");
            this.getBValues();
        }
        this.getCValues();
    }

    private void getCValues() {
        this.c       = this.getMenuChoice(this.firstPrompt  + "c?              ");
        this.cLength = this.getMenuChoice(this.secondPrompt + "c?   ");
        if (c > cLength) {
            System.out.println(this.invalidText + "c");
            this.getCValues();
        }
        this.evaluate();
    }

    private double average(double[] values) {
        double sum = 0;
        for (int i = 0; i < 3; i++)
            sum += values[i];
        return sum / values.length;
    }   

    private int toInt(double value) {
        return (int) value;
    }

    private void evaluate() {
        double aScore = a * (100.0 / aLength);
        double bScore = b * (100.0 / bLength);
        double cScore = c * (100.0 / cLength);
        this.finalGrade = this.average(new double[] {aScore, bScore, cScore});
        System.out.println("\n---------\n");
        System.out.println("Part A           : " + toInt(aScore + 0.5));
        System.out.println("Part B           : " + toInt(bScore + 0.5));
        System.out.println("Part C           : " + toInt(cScore + 0.5));
        System.out.println();
        // Adding 0.5 is equivalent to Math.round()
        // If the value is < 0.5 EX: 0.4 + 0.5 = 0.9
        // The toInt() method will return 0 as it always
        // rounds down. However if it is >= 0.5
        // EX: 0.7 + 0.5 = 1.2 than toInt() will return 1
        System.out.println("Final Grade      : " + toInt(this.finalGrade + 0.5));
    }

    public static void main(String[] args) {
        PetriniV02 program = new PetriniV02();
        program.getAValues();
        // rEAsourCE lEAk: 'sCANneR' iS neVEr clOSeD 
        program.scanner.close();
    }
}