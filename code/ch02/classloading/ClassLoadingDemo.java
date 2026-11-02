public class ClassLoadingDemo {

    static class Engine {
        static {
            System.out.println("  [Engine class is being initialized]");
        }
        static void start() {
            System.out.println("  Engine started");
        }
    }

    static {
        System.out.println("[ClassLoadingDemo class is being initialized]");
    }

    public static void main(String[] args) {
        System.out.println("main() begins");
        System.out.println("Loader of String:           " + String.class.getClassLoader());
        System.out.println("Loader of this class:       " + ClassLoadingDemo.class.getClassLoader().getName());
        System.out.println("Parent of application loader: "
                + ClassLoadingDemo.class.getClassLoader().getParent().getName());
        System.out.println("Before first use of Engine");
        Engine.start();
        System.out.println("After first use of Engine");
        Engine.start();
    }
}
