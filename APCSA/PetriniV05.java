public class PetriniV05 {
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
    private int id = PetriniV05.randomNumber(1000, 9999);
    private int[] tests = new int[3];

    public Student() {
        for (int i = 0; i < 3; i++)
            tests[i] = PetriniV05.randomNumber(60, 100);
    }

    public int averageOfTests() {
        int total = 0;
        for (int testScore : this.tests)
            total += testScore;
        return total / 3;
    }

    public void printResults() {
        System.out.println(String.format(
            "%d :  Test 1 : %d\tTest 2 : %d\tTest 3 : %d\tAvg : %d",
            this.id, 
            this.tests[0],
            this.tests[1],
            this.tests[2],
            this.averageOfTests()
        ));
    }

    public void addExtraCredit() {
        for (int i = 0; i < 3; i++)
            this.tests[i] += PetriniV05.randomNumber(1, 5);
    }
}