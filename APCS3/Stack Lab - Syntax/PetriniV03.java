import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.Stack;

import java.util.regex.Pattern;

public class PetriniV03 {
    public static Stack<Character> stack = new Stack<>();

    public static Pattern syntax = Pattern.compile("[\\(\\)\\[\\]{}<>\\\"]");
    public static Pattern opening = Pattern.compile("[\\(\\[\\{<]");

    public static char[] pairs = new char[]{
        '(', ')', '{', '}', '[', ']', '<', '>', '\"', '\"'
    };

    public static boolean checkPair(char start, char end, char c, String input) {
        if (!stack.empty() && stack.peek() == start) {
            stack.pop();
            return true;
        } else if (stack.empty() || !stack.empty() && stack.peek() != start) {
            System.out.println("Bad  : " + input);
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("Lab3\\CS303.txt"));
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            boolean bad = false;
            boolean ignore = false;
            TOP:
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '\"') {
                    if (!stack.isEmpty() && stack.peek() != '\"')
                        stack.add(c);
                    ignore = !ignore;
                }
                if (ignore || !syntax.matcher(Character.toString(c)).find())
                    continue;
                if (opening.matcher(Character.toString(c)).find() || (c == '\"' && !stack.isEmpty() && stack.peek() != '\"')) {
                    stack.add(c);
                    continue;
                }
                for (int j = 0; j < pairs.length; j += 2) {
                    if (c != pairs[j] && c != pairs[j + 1])
                        continue;
                    if (!checkPair(pairs[j], pairs[j + 1], c, input)) {
                        bad = true;
                        break TOP;
                    }
                }
            }
            if (!bad && stack.size() == 0)
                System.out.println("Good : " + input);
            else if (stack.size() > 0 && !bad)
                System.out.println("Bad  : " + input);
            bad = false;
            stack.clear();
        }
        scanner.close();
    }
}
