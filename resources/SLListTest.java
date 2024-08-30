import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SLListTest {
    @Test
    public void testEmptySize() {
        SLList<String> L = new SLList<>();
        assertEquals(0, L.size());
    }

    @Test
    public void testAddAndSize() {
        SLList<String> L = new SLList<>();
        L.addLast("99");
        L.addLast("99");
        assertEquals(2, L.size());
    }


    @Test
    public void testAddAndRemoveLast() {
        SLList<String> L = new SLList<>();
        L.addLast("99");
        assertEquals("99", L.getLast());
        L.addLast("36");
        assertEquals("36", L.getLast());
    }


    @Test
    public void testGetAndRemoveLast() {
        SLList<String> L = new SLList<>();
        assertNull(L.get(0));
        L.addLast("99");
        L.addLast("36");
        assertEquals("99", L.get(0));
        assertEquals("36", L.get(1));
        assertEquals("36", L.removeLast());
    }


    @Test
    public void testRemove() {
        SLList<String> L = new SLList<>();
        L.addLast("99");
        assertEquals("99", L.get(0));
        L.addLast("36");
        assertEquals("99", L.get(0));
        L.removeLast();
        assertEquals("99", L.getLast());
        L.addLast("100");
        assertEquals("100", L.getLast());
        assertEquals(2, L.size());
    }

    /** Tests insertion of a large number of items.*/
    @Test
    public void testInsertAndGetLast() {
        SLList<String> L = new SLList<>();
        L.insert("999", 1);
        L.insert("99", 0);
        L.insert("36", 0);
        L.insert("47", 1);
        assertEquals("99",L.getLast());
    }
}
