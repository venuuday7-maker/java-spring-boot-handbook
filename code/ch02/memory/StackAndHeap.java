public class StackAndHeap {

    static class Counter {
        int value;
    }

    static void increment(int number, Counter counter) {
        number++;          // changes only the local copy on this method's stack frame
        counter.value++;   // changes the object on the heap, shared by every reference
    }

    static int depth = 0;

    static void recurseForever() {
        depth++;
        recurseForever();
    }

    public static void main(String[] args) {
        int number = 10;
        Counter counter = new Counter();
        increment(number, counter);
        System.out.println("number        = " + number);
        System.out.println("counter.value = " + counter.value);

        try {
            recurseForever();
        } catch (StackOverflowError e) {
            System.out.println("Caught StackOverflowError after many frames: " + (depth > 1000));
        }
    }
}
