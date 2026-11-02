public class PrimitiveRanges {
    public static void main(String[] args) {
        System.out.println("byte    " + Byte.SIZE + " bits: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
        System.out.println("short   " + Short.SIZE + " bits: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
        System.out.println("int     " + Integer.SIZE + " bits: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("long    " + Long.SIZE + " bits: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
        System.out.println("float   " + Float.SIZE + " bits: max " + Float.MAX_VALUE);
        System.out.println("double  " + Double.SIZE + " bits: max " + Double.MAX_VALUE);
        System.out.println("char    " + Character.SIZE + " bits: " + (int) Character.MIN_VALUE
                + " to " + (int) Character.MAX_VALUE);
        boolean active = true;
        System.out.println("boolean values: true/false, e.g. active = " + active);

        // Default values apply to fields, not to local variables
        System.out.println("Default int field: " + Defaults.count + ", boolean: " + Defaults.flag
                + ", String: " + Defaults.name);
    }

    static class Defaults {
        static int count;
        static boolean flag;
        static String name;
    }
}
