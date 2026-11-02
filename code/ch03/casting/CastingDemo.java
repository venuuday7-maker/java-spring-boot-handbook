public class CastingDemo {
    public static void main(String[] args) {
        // Widening: automatic, safe
        int i = 42;
        long l = i;
        double d = i;
        System.out.println("widening: " + i + " -> " + l + " -> " + d);

        // Narrowing: explicit, may lose information
        double price = 3.99;
        int whole = (int) price;              // truncates, does not round
        System.out.println("(int) 3.99 = " + whole + ", Math.round(3.99) = " + Math.round(price));
        System.out.println("(int) -3.99 = " + (int) -3.99);

        // Integer division
        System.out.println("7 / 2 = " + (7 / 2));
        System.out.println("7 / 2.0 = " + (7 / 2.0));
        System.out.println("(double) 7 / 2 = " + ((double) 7 / 2));
        System.out.println("(double) (7 / 2) = " + ((double) (7 / 2)));

        // char is a number
        char letter = 'A';
        System.out.println("'A' + 1 = " + ('A' + 1));
        System.out.println("(char) ('A' + 1) = " + (char) ('A' + 1));
        letter++;
        System.out.println("letter after ++ = " + letter);

        // Compound assignment hides a cast
        byte b = 10;
        b += 5;                               // OK: means b = (byte) (b + 5)
        System.out.println("byte after += 5: " + b);

        // Mixed arithmetic is promoted
        byte x = 100, y = 100;
        int z = x + y;                        // byte + byte is an int
        System.out.println("100 + 100 as bytes promoted to int: " + z);
    }
}
