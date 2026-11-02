/**
 * Finds the largest value in an array using the algorithm
 * described in the chapter: remember the best value seen so far.
 */
public class LargestNumber {

    static int findLargest(int[] numbers) {
        int largest = numbers[0];                // step 1: assume the first is the largest
        for (int i = 1; i < numbers.length; i++) { // step 2: look at every other number
            if (numbers[i] > largest) {          // step 3: is this one bigger?
                largest = numbers[i];            //         if so, remember it
            }
        }
        return largest;                          // step 4: report the answer
    }

    public static void main(String[] args) {
        int[] marks = {42, 77, 15, 98, 63};
        System.out.println("Marks: " + java.util.Arrays.toString(marks));
        System.out.println("Highest mark: " + findLargest(marks));
    }
}
