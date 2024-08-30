/**
 * Methods in a class always have to take special cases into account.
 * Instead of adding more checks, and to control complexity, we make the SLList the "same" at all times.
 * We can do this by creating a special node that is always present, which we will call a "sentinel" node.
 * The sentinel node will hold a value, which we don't care about.
 */
public class SLList<T> implements List61B<T> {
    /**
     * IntNode hides the detail that there exists a null link from the user
     * <p>
     * This static inner class represent a node of Linked List
     * <p>
     * Declaring a nested class as static means that methods inside the static class
     * can not access any of the members of the enclosing class (addFirst, getFirst etc.)
     * AND CAN MAKE inner instance WITHOUT outer instance.
     */
    private static class IntNode<Item> {
        public Item item;
        public IntNode<Item> next;

        public IntNode(Item i, IntNode<Item> n) {
            item = i;
            next = n;
        }
    }
    // The first item (if exists) is at sentinel.next
    private final IntNode<T> sentinel;

    public SLList(T x) {
        sentinel = new IntNode<>(null, null);
        sentinel.next = new IntNode<>(x, null);
    }
    public SLList() {
        sentinel = new IntNode<>(null, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(T x) {
        sentinel.next = new IntNode<>(x, sentinel.next);
    }

    /** Retrieves the front item from the list. */
    public T getFirst() {
        return sentinel.next.item;
    }

    @Override
    public T getLast() {
        if (sentinel.next == null) return null;
        return getIntNode(sentinel.next, size() - 1).item;
    }

    @Override
    public T removeLast() {
        IntNode<T> last = null;
        if (sentinel.next == null) return null;
        last = getIntNode(sentinel.next, size() - 1);
        getIntNode(sentinel.next, size() - 2).next = null;
        return last.item;
    }

    @Override
    public T get(int i) {
        if (sentinel.next == null || i >= size()) return null;
        return getIntNode(sentinel.next, i).item;
    }

    @Override
    public void insert(T x, int position) {
        if (position >= 0 && position <= size()) {
            if (size() < 2) {
                addFirst(x);
            }
            else {
                IntNode<T> point = getIntNode(sentinel.next, position - 1);
                point.next = new IntNode<>(x, point.next);
            }
        }
    }

    private IntNode<T> getIntNode(IntNode<T> p, int index) {
        if (index == 0) return p;
        return getIntNode(p.next, index - 1);
    }

    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        IntNode<T> p = sentinel;
        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode<>(x, null);
    }

    /** Overload. Returns the size of the list starting at IntNode p. */
    private static <T> int size(IntNode<T> p) {
        if (p.next == null) {
            return 1;
        }
        return 1 + size(p.next);
    }

    /**
     * Instead of iterate all the nodes to calculate size, we can add SIZE attribute to track size changes.
     * In this case, the increase in memory is trivial.
     * This practice of saving important data to speed up retrieval is sometimes known as caching.
     */
    public int size() {
        return size(sentinel) - 1;
    }

    public void deleteFirst() {
        if (sentinel.next != null) {
            sentinel.next = sentinel.next.next;
        }
    }

    public static <T> SLList<T> of(T... args) {
        SLList<T> result;
        IntNode<T> p;

        if (args.length > 0) {
            result = new SLList<>(args[0]);
        } else {
            return new SLList<>();
        }

        int k;
        for (k = 1, p = result.sentinel.next; k < args.length; k += 1, p = p.next) {
            p.next = new IntNode<>(args[k], null);
        }
        return result;
    }

    public static void main(String[] args) {
        SLList<Integer> l = SLList.of(1, 2, 3, 4, 5);
    }
}
