/**
 * Higher Order Functions.<p>
 * Samples in Python:
 * <blockquote><pre>
 * def tenX(x):
 *     return 10*x
 *
 * def do_twice(f, x):
 *     return f(f(x))
 * </pre></blockquote><p>
 * In old school Java (Java 7 and earlier), memory boxes (variables) could not contain pointers to functions.
 * What that means is that we could not write a function that has a "Function" type,
 * as there was simply no type for functions
 */
public class HoFSamples {

    static class TenX implements IntUnaryFunction {
        /* Returns ten times the argument. */
        public int apply(int x) {
            return 10 * x;
        }
    }

    public static int do_twice(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }

    public static void main(String[] args) {
        System.out.println(do_twice(new TenX(), 2));
    }
}
