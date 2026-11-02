public class ShowVersion {
    public static void main(String[] args) {
        System.out.println("Java feature version: " + Runtime.version().feature());
        System.out.println("Java vendor: " + System.getProperty("java.vendor"));
    }
}
