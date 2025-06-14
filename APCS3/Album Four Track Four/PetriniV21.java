import java.util.Scanner;
import java.util.HashMap;

public class PetriniV21 {
    private static HashMap<Long, Long> prev = new HashMap<>();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("How many steps can you take at once?\t");
        int num = Integer.parseInt(s.nextLine());
        for (int i = 1; i <= 50; i++)
            System.out.printf(
                    "If you can take %d steps at a time you can traverse a %d sized set of stairs in %,d different ways\n",
                    num, i, getStepPossibilites(i, num));

        s.close();
    }

    public static long getStepPossibilites(int steps, int num) {
        long total = 0;
        if (steps == 0)
            return 1;
        for (int i = 1; i <= num; i++) {
            if (steps - i >= 0) {
                Long amount;
                if (!prev.containsKey((long) (steps - i))) {
                    amount = getStepPossibilites(steps - i, num);
                    prev.put((long) (steps - i), amount);
                } else {
                    amount = prev.get((long) (steps - i));
                }
                total += amount;
            }
        }
        return total;
    }
}