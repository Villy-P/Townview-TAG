import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PetriniV18 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("emails.txt"));
        String regex = "^\\w\\S+\\w@\\w+\\..*\\w$";
        System.out.println(regex.length());
        while (s.hasNextLine()) {
            String e = s.nextLine();
            System.out.println(e + " is " + (e.matches(regex) ? "" : "in") + "valid");
        }
        s.close();
    }
}