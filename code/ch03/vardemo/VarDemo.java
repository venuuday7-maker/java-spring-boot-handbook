import java.util.ArrayList;
import java.util.List;

public class VarDemo {
    public static void main(String[] args) {
        var count = 10;                       // int
        var price = 19.99;                    // double
        var name = "Asha";                    // String
        var items = new ArrayList<String>();  // ArrayList<String>
        items.add("pen");

        System.out.println(((Object) count).getClass().getSimpleName());
        System.out.println(((Object) price).getClass().getSimpleName());
        System.out.println(name.getClass().getSimpleName());
        System.out.println(items.getClass().getSimpleName() + " " + items);

        for (var item : items) {
            System.out.println("item: " + item);
        }

        // The type is fixed at compile time:
        // count = "ten";   // would not compile: String cannot be converted to int

        List<String> declared = items;        // prefer a clear declared type when it helps readers
        System.out.println(declared.size());
    }
}
