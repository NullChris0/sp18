/**
 * The new List61B is what Java calls an interface.
 * It is essentially a contract that specifies what a list must be able to do,
 * but it doesn't provide any implementation for those behaviors
 */
public interface List61B<Item> {
    public void addFirst(Item x);
    public void addLast(Item y);
    public Item getFirst();
    public Item getLast();
    public Item removeLast();
    public Item get(int i);
    public void insert(Item x, int position);
    public int size();
    default public void print() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

}
