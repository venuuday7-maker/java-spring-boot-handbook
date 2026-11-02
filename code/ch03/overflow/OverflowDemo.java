public class OverflowDemo {
    public static void main(String[] args) {
        int max = Integer.MAX_VALUE;
        System.out.println("max           = " + max);
        System.out.println("max + 1       = " + (max + 1));          // wraps around silently
        System.out.println("min - 1       = " + (Integer.MIN_VALUE - 1));

        int millions = 3_000_000;
        System.out.println("int product   = " + (millions * millions));          // overflows
        System.out.println("long product  = " + ((long) millions * millions));   // correct

        try {
            Math.addExact(max, 1);
        } catch (ArithmeticException e) {
            System.out.println("addExact says: " + e.getMessage());
        }

        byte b = (byte) 200;
        System.out.println("(byte) 200    = " + b);

        long big = 3_000_000_000L;
        System.out.println("(int) 3 billion = " + (int) big);
    }
}
