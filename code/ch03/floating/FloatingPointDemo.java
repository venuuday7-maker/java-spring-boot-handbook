import java.math.BigDecimal;

public class FloatingPointDemo {
    public static void main(String[] args) {
        double sum = 0.1 + 0.2;
        System.out.println("0.1 + 0.2          = " + sum);
        System.out.println("0.1 + 0.2 == 0.3   ? " + (sum == 0.3));
        System.out.println("close enough       ? " + (Math.abs(sum - 0.3) < 1e-9));

        float f = 0.1f + 0.2f;
        System.out.println("float 0.1f + 0.2f  = " + f);
        System.out.println("float 16777216+1   = " + (16777216f + 1f));

        BigDecimal exact = new BigDecimal("0.1").add(new BigDecimal("0.2"));
        System.out.println("BigDecimal sum     = " + exact);
        System.out.println("BigDecimal == 0.3  ? " + exact.equals(new BigDecimal("0.3")));

        System.out.println("1.0 / 0            = " + (1.0 / 0));
        System.out.println("0.0 / 0.0          = " + (0.0 / 0.0));
        System.out.println("NaN == NaN         ? " + (Double.NaN == Double.NaN));
        System.out.println("isNaN(0.0 / 0.0)   ? " + Double.isNaN(0.0 / 0.0));

        try {
            int zero = 0;
            System.out.println(10 / zero);
        } catch (ArithmeticException e) {
            System.out.println("int 10 / 0         -> " + e.getMessage());
        }
    }
}
