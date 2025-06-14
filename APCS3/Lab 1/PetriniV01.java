package Lab1;
import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class PetriniV01 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("Lab1\\binary.txt"));
        while (scanner.hasNextLine()) {
            String binary = scanner.nextLine();
            int value = 0;
            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(i) == '0') 
                    continue;
                value += Math.pow(2, binary.length() - i - 1);
            }
            System.out.printf("%s in base 2 equals %d in base 10.\n\n", binary, value);
        }
    }
}