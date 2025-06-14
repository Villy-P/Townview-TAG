import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetriniV03 {
    private static Scanner scanner = new Scanner(System.in);

    private static double getMenuChoice(String prompt) {
        while (true) {
            // Ask user for input
            System.out.print(prompt);
            String userInput = scanner.nextLine();
            // Check if user input is an integer
            // Used regex101.com to get regex pattern
            Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.[0-9]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            // If the user input is an double, check if it is within the range specified
            if (matcher.find()) {
                double value = Double.parseDouble(userInput);
                if (value < 0)
                    continue;
                return value;
            } else {
                continue;
            }
        }
    }

    // Oh wow I made this function in Typescript to get into this class!
    public static double getLargest(double a, double b, double c) {
        if (a > b && a > c)
            return a;
        else if (b > c)
            return b;
        return c;
    }

    public static void main(String[] args) {
        System.out.println("Valerius Petrini");
        System.out.println("Lab 03\n");
        double firstLeg  = getMenuChoice("Enter the first leg of the triangle    ");
        double secondLeg = getMenuChoice("Enter the second leg of the triangle   ");
        System.out.printf("The hypotenuse of the triangle is %.2f\n", Math.sqrt((firstLeg * firstLeg) + (secondLeg * secondLeg)));
        System.out.println();
        double radius    = getMenuChoice("Enter the radius of the circle         ");
        System.out.printf("The circumference of the circle is %.2f\n", (2 * Math.PI * radius));
        System.out.printf("The area of the circle is %.2f\n", (Math.PI * Math.pow(radius, 2)));
        System.out.printf("The volume of sphere is %.2f\n", (4.0/3.0) * Math.PI * Math.pow(radius, 3));
        System.out.println("-----------");
        System.out.println("Extra Credit\n");
        System.out.println("The largest number entered was " + getLargest(firstLeg, secondLeg, radius));
        scanner.close();
    }
}