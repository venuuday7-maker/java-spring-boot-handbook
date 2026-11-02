public class AverageCalculator {

    /** Returns the average of the given scores, or 0 if there are none. */
    static double average(int[] scores) {
        if (scores.length == 0) {
            return 0;
        }
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return (double) total / scores.length;
    }

    public static void main(String[] args) {
        int[] scores = {70, 85, 90, 65};
        System.out.println("Average: " + average(scores));
        System.out.println("Empty:   " + average(new int[0]));
    }
}
