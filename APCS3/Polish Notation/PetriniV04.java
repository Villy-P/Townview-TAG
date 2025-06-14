import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.Stack;

public class PetriniV04 {
    private static Stack<String> operands = new Stack<>();
    private static Stack<String> output = new Stack<>();

    private static String operandMatch = "[\\+\\*\\-/^]";
    private static String numberMatch = "\\d+";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("CS304.txt"));
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            processInput(input);
        }
        scanner.close();
    }

    private static void processInput(String input) {
        String[] splitInput = input.split("\\s+");
        for (String i : splitInput) {
            processToken(i);
        }
        while (!operands.isEmpty())
            output.push(operands.pop());
        printOutput();
        output.clear();
    }

    private static void processToken(String token) {
        if (token.matches(numberMatch))
            output.push(token);
        if (token.matches(operandMatch)) {
            processOperand(token);
        }
        if (token.matches("\\("))
            operands.push(token);
        if (token.matches("\\)"))
            processClosingParenthesis();
    }

    private static void processOperand(String operand) {
        final int topPrecedence = operands.isEmpty() ? 0 : precedence(operands.peek());
        final int iPrecedence = precedence(operand);
        while (!operands.isEmpty() && !operands.peek().equals("(") && (topPrecedence > iPrecedence)) {
            output.push(operands.pop());
        }
        operands.push(operand);
    }

    private static void processClosingParenthesis() {
        while (!operands.isEmpty() && !operands.peek().equals("("))
            output.push(operands.pop());
        operands.pop();
    }

    private static void printOutput() {
        for (String i : output)
            System.out.print(i + " ");
        System.out.println();
    }

    public static int precedence(String c) {
		if (c.equals("+") || c.equals("-"))
            return 1;
		if (c.equals("*") || c.equals("/"))
            return 2;
		if (c.equals("^"))
            return 3;
		return 0;
	}
}
