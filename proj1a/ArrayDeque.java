public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int CAPACITY = 8;

    public ArrayDeque() {
        items = (T[]) new Object[CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** 计算索引的环形递增 */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /** 计算索引的环形递减 */
    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /** 检查是否需要扩容 */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int start = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[start];
            start = plusOne(start);
        }
        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    private void checkReduce() {
        double factor = (double) size / items.length;
        if (factor < 0.25 && items.length > 16) {
            resize((int) (items.length * 2 * factor));
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int start = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[start] + " ");
            start = plusOne(start);
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T removedItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        checkReduce();
        return removedItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T removedItem = items[nextLast];
        items[nextLast] = null;
        size--;
        checkReduce();
        return removedItem;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        int actualIndex = (plusOne(nextFirst) + index) % items.length;
        return items[actualIndex];
    }
}
