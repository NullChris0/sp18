import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.NoSuchElementException;

/***
 * An array-based implementation of Map61B.
 * Notice each key can only have one value at a time.
 ***/
public class ArrayMap<K, V> implements Map61B<K, V>, Iterable<Map.Entry<K, V>> {

    private class ArrayMapIterator<E> implements Iterator<E> {
        private int index;
        private final IterationType type;

        public ArrayMapIterator(IterationType type) {
            this.index = 0;
            this.type = type;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result;
            switch (type) {
                case KEYS:
                    result = (E) keys[index];
                    break;
                case VALUES:
                    result = (E) values[index];
                    break;
                case ENTRIES:
                    result = (E) new AbstractMap.SimpleEntry<>(keys[index], values[index]);
                    break;
                default:
                    throw new IllegalStateException("Unknown iteration type");
            }
            index++;
            return result;
        }
    }

    private final K[] keys;
    private final V[] values;
    int size;
    public enum IterationType {
        KEYS, VALUES, ENTRIES
    }

    @SuppressWarnings("unchecked")
    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }
    public Iterator<K> keyIterator() {
        return new ArrayMapIterator<>(IterationType.KEYS);
    }

    public Iterator<V> valueIterator() {
        return new ArrayMapIterator<>(IterationType.VALUES);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(IterationType.ENTRIES);
    }

//    public Iterable<K> keys() {
//        return this::keyIterator;
//    }

    public Iterable<V> values() {
        return this::valueIterator;
    }
    // default
    public Iterable<Map.Entry<K, V>> items() {
        return this;
    }

    /**
     * Returns the index of the key, if it exists. Otherwise returns -1.
     **/
    private int keyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key))
                return i;
        }
        return -1;
    }

    public boolean containsKey(K key) {
        int index = keyIndex(key);
        return index > -1;
    }

    public void put(K key, V value) {
        int index = keyIndex(key);
        if (index == -1) {
            keys[size] = key;
            values[size] = value;
            size += 1;
        } else {
            values[index] = value;
        }
    }

    public V get(K key) {
        int index = keyIndex(key);
        if (index == -1) {
            throw new IllegalArgumentException("Key not found");
        }
        return values[index];  // if index not existed, values[-1] will raise RuntimeError
    }

    // static methods can't reach class generic
    public static <K, V> V get(Map61B<K, V> map, K key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public int size() {
        return size;
    }

        /**
         * return ArrayList of keys.
         */
    public List<K> keys() {
        List<K> keyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            keyList.add(keys[i]);
        }
        return keyList;
    }

    public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map) {
        List<K> keylist = map.keys();
        K largest = keylist.get(0);
        for (K k: keylist) {
            if (k.compareTo(largest) > 0) {
                largest = k;
            }
        }
        return largest;
    }

    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (Map.Entry<K, V> x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    public static <K, V> ArrayMap<K, V> of(K k1, V v1, Object... alters) {
        ArrayMap<K, V> map = new ArrayMap<>();
        map.put(k1, v1);

        if (alters.length % 2 != 0) {
            throw new IllegalArgumentException("Must have an even number of arguments");
        }
        for (int i = 0; i < alters.length; i += 2) {
            @SuppressWarnings("unchecked")
            K key = (K) alters[i];
            @SuppressWarnings("unchecked")
            V value = (V) alters[i + 1];
            map.put(key, value);
        }
        return map;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayMap<K, V> o = (ArrayMap<K, V>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {  // foreach with builtin array, which has some null elements, will get mistake
            if (!o.containsKey(keys[i])) {
                return false;
            } else if (!o.get(keys[i]).equals(this.get(keys[i]))) {
                return false;
            }
        }
        return true;
    }
}
