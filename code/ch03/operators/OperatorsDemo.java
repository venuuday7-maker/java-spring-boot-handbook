public class OperatorsDemo {
    public static void main(String[] args) {
        // Precedence and parentheses
        System.out.println("2 + 3 * 4   = " + (2 + 3 * 4));
        System.out.println("(2 + 3) * 4 = " + ((2 + 3) * 4));

        // Remainder, including negative numbers
        System.out.println("17 % 5  = " + (17 % 5));
        System.out.println("-17 % 5 = " + (-17 % 5));
        System.out.println("Math.floorMod(-17, 5) = " + Math.floorMod(-17, 5));

        // Prefix and postfix
        int n = 5;
        int a = n++;
        int b = ++n;
        System.out.println("a = " + a + ", b = " + b + ", n = " + n);

        // Short-circuit logic
        int[] data = {};
        boolean safe = data.length > 0 && data[0] > 10;   // right side never runs
        System.out.println("safe = " + safe);

        // Ternary
        int age = 20;
        String category = age >= 18 ? "adult" : "minor";
        System.out.println(category);

        // Comparing strings
        String s1 = "java";
        String s2 = new String("java");
        System.out.println("s1 == s2      : " + (s1 == s2));
        System.out.println("s1.equals(s2) : " + s1.equals(s2));

        // Bitwise and shifts
        System.out.println("5 & 3  = " + (5 & 3));
        System.out.println("5 | 3  = " + (5 | 3));
        System.out.println("5 ^ 3  = " + (5 ^ 3));
        System.out.println("1 << 4 = " + (1 << 4));
        System.out.println("-16 >> 2  = " + (-16 >> 2));
        System.out.println("-16 >>> 28 = " + (-16 >>> 28));

        // String concatenation traps
        System.out.println("1 + 2 + \"x\" = " + (1 + 2 + "x"));
        System.out.println("\"x\" + 1 + 2 = " + ("x" + 1 + 2));
    }
}
