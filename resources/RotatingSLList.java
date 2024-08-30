/**
 * By using the extends keyword, subclasses inherit all members of the parent class. "Members" includes:
 * <p>
 * All instance and static variables
 * All methods
 * All nested classes
 * Note that constructors are not inherited, and private members cannot be directly accessed by subclasses.
 */
public class RotatingSLList<T> extends SLList<T> {

    public RotatingSLList(T x) {
        super(x);
    }
    public void rotateRight() {
        T x = removeLast();
        addFirst(x);
    }

    public static void main(String[] args) {
        RotatingSLList<Integer> S = new RotatingSLList<Integer>(0);
        for (int i = 1; i < 10; i++)
            S.addLast(i);
        SLList<Integer> L = S;
        S = (RotatingSLList<Integer>) L;  // casting, because L is actually a reference of RotatingSLList
//        RotatingSLList<Integer> S2 = L;  // can't compile
        L.removeLast();
//        L.rotateRight();  // can't compile
        S.print();
    }
}
