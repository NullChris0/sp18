public class Inheritance {

    interface Dog {
        default int bark() {
            System.out.println("Dog-Bark");
            return 0;
        }
    }

    static class Tree {
        public int bark() {
            System.out.println("Tree-Bark");
            return 0;
        }
    }

    static class Mutant extends Tree implements Dog {

    }

    public static void main(String[] args) {
        Dog n = new Mutant();
        n.bark();  // static type is Dog, use Dog's method
    }
}
