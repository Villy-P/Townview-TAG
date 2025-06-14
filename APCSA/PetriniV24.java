import java.util.Scanner;
import java.util.regex.*;

public class PetriniV24 {
    public static Scanner s = new Scanner(System.in);

    public static String getInput() {
        System.out.print("Enter a sentence:\t");
        return s.nextLine();
    }

    public static String strChanger(String str) {
        String newString = "";
        for (String word : str.split(" ")) {
            String e = "";
            for (String letter : word.split("")) {
                if (Pattern.compile("[aeiou]").matcher(letter).find())
                    e += letter.repeat(3);
                else 
                    e += letter;
            }
            if (Pattern.compile("[aeiou]").matcher(word.substring(0, 1)).find())
                newString += e.substring(1) + "wook ";
            else
                newString += e.substring(1) + (Pattern.compile("[aeiou]").matcher(e.substring(0, 1)).find() ? e.substring(0, 1).repeat(3) : e.substring(0, 1)) + "eek ";
        }
        return newString;
    }

    public static void main(String[] Poob) {
        String str = getInput();
        str = str.toLowerCase();
        str = str.replaceAll("[^A-Za-z\\s]", "");
        str = strChanger(str);
        System.out.println("The monkey says: '" + str + "'");
    }
}