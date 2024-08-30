/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

/* Invariants:
 addLast: The next item we want to add, will go into position size
 getLast: The item we want to return is in position size - 1
 size: The number of items in the list should be size.
*/
/*
** implements List61B<Item> is essentially a promise.
** AList is saying "I promise I will have and define all the attributes and behaviors
   specified in the List61B interface"
*/
public class AList<T> implements List61B<T> {
    private T[] items;
    private int size;

    /** Creates an empty list. */
    public AList() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    @Override
    public void addFirst(T x) {

    }

    /** Inserts X into the back of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[size] = x;
        size = size + 1;
    }

    @Override
    public T getFirst() {
        return null;
    }

    /** Returns the item from the back of the list. */
    public T getLast() {
        return items[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public T get(int i) {
        return items[i];
    }

    @Override
    public void insert(T x, int position) {

    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public T removeLast() {
        T x = getLast();
        items[size - 1] = null;
        size = size - 1;
        return x;
    }
} 