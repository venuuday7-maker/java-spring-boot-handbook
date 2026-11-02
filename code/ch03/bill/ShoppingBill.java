import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Money should never be stored in double. This example computes a bill two ways:
 * with whole cents in a long, and with BigDecimal.
 */
public class ShoppingBill {
    private static final int TAX_PERCENT = 18;

    public static void main(String[] args) {
        // Way 1: integer cents
        long penCents = 1999;          // 19.99
        int quantity = 3;
        long subtotalCents = penCents * quantity;
        long taxCents = Math.round(subtotalCents * TAX_PERCENT / 100.0);
        long totalCents = subtotalCents + taxCents;
        System.out.println("Subtotal: " + format(subtotalCents));
        System.out.println("Tax 18%:  " + format(taxCents));
        System.out.println("Total:    " + format(totalCents));

        // Way 2: BigDecimal
        BigDecimal price = new BigDecimal("19.99");
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
        System.out.println("BigDecimal total: " + subtotal.add(tax));

        // The wrong way: ten payments of 0.10 stored in a double
        double wrong = 0;
        for (int i = 0; i < 10; i++) {
            wrong += 0.10;
        }
        System.out.println("double: ten payments of 0.10 = " + wrong);
        System.out.println("double equals 1.0 ? " + (wrong == 1.0));
    }

    private static String format(long cents) {
        return String.format("%d.%02d", cents / 100, cents % 100);
    }
}
