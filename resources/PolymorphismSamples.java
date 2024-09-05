import java.util.Comparator;
/**
 * Suppose we want to write a python program that prints a string representation of the larger of two objects.
 * There are two approaches to this.<p>
 * Explicit HoF Approach:
 * <blockquote><pre>
 * def print_larger(x, y, compare, stringify):
 *     if compare(x, y):
 *         return stringify(x)
 *     return stringify(y)
 * </pre></blockquote>
 * Subtype Polymorphism Approach:
 * <blockquote><pre>
 * def print_larger(x, y):
 *     if x.largerThan(y):
 *         return x.str()
 *     return y.str()
 * </pre></blockquote><p>
 * Using the explicit higher order function approach, you have a common way to print out the larger of two objects. In contrast, in the subtype polymorphism approach, the object itself makes the choices. The largerFunction that is called is dependent on what x and y actually are.
 */
public class PolymorphismSamples {

    static class Dog implements Comparable<Dog>{

        private static class NameComparator implements Comparator<Dog> {
            public int compare(Dog a, Dog b) {
                return a.name.compareTo(b.name);
            }
        }

        public static Comparator<Dog> getNameComparator() {
            return new NameComparator();
        }

        private String name;
        private int size;

        public Dog(String n, int s) {
            name = n;
            size = s;
        }

        public void bark() {
            System.out.println(name + " says: bark");
        }

        public int compareTo(Dog uddaDog) {
            return this.size - uddaDog.size;
        }
    }

    static class Maximizer {

        /**
         * The Class itself isn't generic, but static method is.
         * the generic identifier can have a type upper bound, is behind static clarify
         * @param items Any Type of array
         * @return Any Type of the max array element
         */
        public static <T extends Comparable<T>> T max(T[] items) {
            int maxDex = 0;
            for (int i = 0; i < items.length; i += 1) {
                int cmp = items[i].compareTo(items[maxDex]);
                if (cmp > 0) {
                    maxDex = i;
                }
            }
        return items[maxDex];
        }
    }

    public static void main(String[] args) {
        Dog[] dogs = {new Dog("Elyse", 3), new Dog("Sture", 9), new Dog("Benjamin", 15)};
        Dog maxDog = Maximizer.max(dogs);
        maxDog.bark();

        Comparator<Dog> nc = Dog.getNameComparator();
    }
}
