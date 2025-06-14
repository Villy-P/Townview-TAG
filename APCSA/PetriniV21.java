import java.util.Scanner;
import java.util.regex.Pattern;

public class PetriniV21 {
    public static Scanner scanner = new Scanner(System.in);

    public static String[] vowels = {"a", "e", "i", "o", "u"};
    
    public static String getNewVowel(String vowel) {
        String n = vowels[(int)(Math.random() * 5)];
        return n == vowel.toLowerCase() ? getNewVowel(vowel) : n;
    }

    public static void main(String[] args) {
        System.out.print("Enter some text :\t");
        String ans = scanner.nextLine();
        String newString = "";
        for (String s : ans.split("")) {
            if (Pattern.compile("[aeiou]").matcher(s).find())
                newString += getNewVowel(s);
            else if (Pattern.compile("[AEIOU]").matcher(s).find())
                newString += getNewVowel(s).toUpperCase();
            else 
                newString += s;
        }
        System.out.println("                \t" + newString);
    }
}