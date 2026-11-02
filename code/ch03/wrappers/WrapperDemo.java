import java.util.ArrayList;
import java.util.List;

public class WrapperDemo {
    public static void main(String[] args) {
        // Autoboxing and unboxing
        Integer boxed = 100;          // int -> Integer
        int unboxed = boxed + 1;      // Integer -> int
        System.out.println("boxed = " + boxed + ", unboxed + 1 = " + unboxed);

        List<Integer> numbers = new ArrayList<>();
        numbers.add(7);               // collections hold objects, so 7 is boxed
        System.out.println("list = " + numbers);

        // == on wrapper objects compares references
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println("127 == 127 : " + (a == b));
        System.out.println("128 == 128 : " + (c == d));
        System.out.println("128 equals 128 : " + c.equals(d));

        // null cannot be unboxed
        Integer missing = null;
        try {
            int value = missing;
            System.out.println(value);
        } catch (NullPointerException e) {
            System.out.println("Unboxing null -> NullPointerException");
        }

        // Parsing text
        int parsed = Integer.parseInt("1234");
        double rate = Double.parseDouble("7.5");
        System.out.println("parsed = " + parsed + ", rate = " + rate);
        try {
            Integer.parseInt("12a");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE
                + ", compare(3, 9) = " + Integer.compare(3, 9)
                + ", toBinaryString(10) = " + Integer.toBinaryString(10));
    }
}
