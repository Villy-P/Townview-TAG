import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetriniV04 {
    public static Scanner scanner = new Scanner(System.in);

    public static double getMenuChoice(String prompt) {
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

    public static double getL(double r, double h) {
        return (Math.sqrt(Math.pow(r, 2) + Math.pow(h, 2)));
    }

    public static double getCircumference(double r) {
        return 2 * Math.PI * r;
    }

    public static double getRectangleArea(double l, double w) {
        return l * w;
    }

    public static double getTriangleArea(double b, double h) {
        return 0.5 * b * h;
    }

    public static double getTrapezoidArea(double b1, double b2, double h) {
        return 0.5 * (b1 + b2) * h;
    }

    public static double getCircleArea(double r) {
        return Math.PI * Math.pow(r, 2);
    }

    public static double getCubeSurfaceArea(double s) {
        return 6 * Math.pow(s, 2);
    }

    public static double getLateralCylinderSurfaceArea(double r, double h) {
        return 2 * Math.PI * r * h;
    }

    public static double getTotalCylinderSurfaceArea(double r, double h) {
        return 2 * Math.PI * r * (h + r);
    }

    public static double getLateralConeSurfaceArea(double r, double h) {
        return Math.PI * r * getL(r, h);
    }

    public static double getConeSurfaceArea(double r, double h) {
        return Math.PI * r * (getL(r, h) + r);
    }

    public static double getSphereSurfaceArea(double r) {
        return 4 * Math.PI * Math.pow(r, 2);
    }

    public static double getCubeVolume(double l, double w, double h) {
        return l * w * h;
    }

    public static double getCylinderVolume(double r, double h) {
        return getCircleArea(r) * h;
    }

    public static double getPyramidVolume(double l, double w, double h) {
        return (1.0 / 3.0) * l * w * h;
    }

    public static double getConeVolume(double r, double h) {
        return (1.0 / 3.0) * getCircleArea(r) * h;
    }

    public static double getSphereVolume(double r) {
        return (4.0 / 3.0) * Math.PI * Math.pow(r, 3);
    } 

    public static void main(String[] args) throws Exception {
        System.out.println("Valerius Petrini\nLab 04\n");
        double radius = getMenuChoice("Enter the radius       ");
        double height = getMenuChoice("Enter the height       ");
        System.out.printf("\nThe surface area of the cylinder is %.2f\n", getTotalCylinderSurfaceArea(radius, height));
        System.out.printf("The surface area of the cone is %.2f\n", getConeSurfaceArea(radius, height));
        System.out.printf("\nThe volume of the cylinder is %.2f\n", getCylinderVolume(radius, height));
        System.out.printf("The volume of the cone is %.2f\n", getConeVolume(radius, height));
    }
}