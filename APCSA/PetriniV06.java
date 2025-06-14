public class PetriniV06 {
    public static int randomNumber(double min, double max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static void main(String[] args) throws Exception {
        Student[] students = new Student[3];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student();
            students[i].printResults();
        }
        System.out.println("\n\nGrades after extra credit:");
        for (Student student : students) {
            student.addExtraCredit();
            student.printResults();
        }
    }
}

class Student {
    private int id = PetriniV06.randomNumber(1000, 9999);
    private int[] tests = new int[3];

    public Student() {
        for (int i = 0; i < 3; i++)
            tests[i] = PetriniV06.randomNumber(60, 100);
    }

    public int averageOfTests() {
        int total = 0;
        for (int testScore : this.tests)
            total += testScore;
        return total / 3;
    }

    public void printResults() {
        int averageOfTests = this.averageOfTests();
        System.out.println(String.format(
            "%d :  Test 1 : %d\tTest 2 : %d\tTest 3 : %d\tAvg : %d\t%c",
            this.id, 
            this.tests[0],
            this.tests[1],
            this.tests[2],
            averageOfTests,
            this.getGrade(averageOfTests)
        ));
    }

    public void addExtraCredit() {
        for (int i = 0; i < 3; i++)
            this.tests[i] += PetriniV06.randomNumber(1, 5);
    }

    public char getGrade(int testScore) {
        if (testScore > 90)
            return 'A';
        else if (testScore >= 80)
            return 'B';
        else if (testScore >= 75)
            return 'C';
        else if (testScore >= 70)
            return 'D';
        else
            return 'F';
    }
}