/** A basic trivial of Linked List
 *
 */
public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /* recursion */
    public int size() {
        if (rest == null) return 1;
        return 1 + rest.size();
    }
    /* iterative */
    public int iterativeSize() {
        IntList p = this;
        int total = 0;
        while (p != null) {
            total += 1;
            p = p.rest;
        }
        return total;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i >= iterativeSize()) {
            return -1;
        } else if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Osmosis (10 pts) <p>
     * We want to add a method to IntList so that if 2 numbers in a row are the same, we add them together and
     * make one large node. For example:
     * 1 → 1 → 2 → 3 becomes 2 → 2 → 3 which becomes 4 → 3
     */
    public void addAdjacent () {
        IntList one = this, two = rest;
        while (one.rest != null) {
            if (one.first == two.first) {
                one.first += two.first;
                one.rest = two.rest;
                two = two.rest;
            } else {
                one = one.rest;
                two = two.rest;
            }
        }
    }
    public static void main(String[] args) {
        IntList l = new IntList(1, new IntList(1, new IntList(2, new IntList(4, new IntList(5, null)))));
        l.addAdjacent();
        System.out.println(l.size());
        System.out.println(l.get(12));
    }
}
