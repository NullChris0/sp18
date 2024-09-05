import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestArrayMap {

    @Test
    public void testMap() {
        ArrayMap<Integer, Long> am = new ArrayMap<>();
        am.put(2, 5L);
        System.out.println(ArrayMap.get(am, 2));
        System.out.println(ArrayMap.maxKey(am));
        long expected = 5L;
        // method "get" returns Long class(because of type argument)
        // use three ways below:
        assertEquals(expected, am.get(2).longValue());
        assertEquals(new Long(5L), am.get(2));
        assertEquals((Long) expected, am.get(2));
    }

    @Test
    public void testIterator() {
        ArrayMap<String, Integer> map = ArrayMap.of("one", 1, "two", 2, "three", 3);

        // Iterate over keys
        for (String key : map.keys()) {
            System.out.println(key);
        }

        // Iterate over values
        for (Integer value : map.values()) {
            System.out.println(value);
        }

        // Iterate over entries
        for (Map.Entry<String, Integer> entry : map.items()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Or use the iterators directly
        Iterator<Map.Entry<String, Integer>> Iter = map.iterator();
        while (Iter.hasNext()) {
            System.out.println(Iter.next());
        }
        assertEquals("{one=1, two=2, three=3}", map.toString());
    }

    @Test
    public void testEqual() {
        ArrayMap<Integer, Integer> am = new ArrayMap<>();
        for (int i = 0; i < 10; i++) {
            am.put(i, i);
        }
        ArrayMap<Integer, Integer> ma = new ArrayMap<>();
        for (int i = 9; i >= 0; i--) {
            ma.put(i, i);
        }
        assertEquals(am, ma);
        assertNotEquals(am, null);
    }
}
