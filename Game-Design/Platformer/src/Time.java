public class Time {
    public static double getNanoSeconds() {
        return System.nanoTime() / 1000000000.0;
    }
}