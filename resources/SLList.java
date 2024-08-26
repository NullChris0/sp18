/**
 * Methods in a class always have to take special cases into account.
 * Instead of adding more checks, and to control complexity, we make the SLList the "same" at all times.
 * We can do this by creating a special node that is always present, which we will call a "sentinel" node.
 * The sentinel node will hold a value, which we don't care about.
 */
public class SLList {
    /**
     * IntNode hides the detail that there exists a null link from the user
     * <p>
     * This static inner class represent a node of Linked List
     * <p>
     * Declaring a nested class as static means that methods inside the static class
     * can not access any of the members of the enclosing class (addFirst, getFirst etc.)
     * AND CAN MAKE inner instance WITHOUT outer instance.
     */
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    // The first item (if exists) is at sentinel.next
    private IntNode sentinel;

    public SLList(int x) {
        sentinel = new IntNode(-1, null);
        sentinel.next = new IntNode(x, null);
    }
    public SLList() {
        sentinel = new IntNode(-1, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        IntNode p = sentinel;
        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /** Overload. Returns the size of the list starting at IntNode p. */
    private static int size(IntNode p) {
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

    public static SLList of(Integer... args) {
        SLList result;
        IntNode p;

        if (args.length > 0) {
            result = new SLList(args[0]);
        } else {
            return new SLList();
        }

        int k;
        for (k = 1, p = result.sentinel.next; k < args.length; k += 1, p = p.next) {
            p.next = new IntNode(args[k], null);
        }
        return result;
    }

    public static void main(String[] args) {
        SLList l = SLList.of(1, 2, 3, 4, 5);
    }
}
