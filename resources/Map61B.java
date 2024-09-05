import java.util.List;

public interface Map61B<K, V> {
    void put(K key, V value);  // Associate key with value.

    boolean containsKey(K key);  // Checks if map contains the key.

    V get(K key);  // Returns value, assuming key exists.

    List<K> keys();  // Returns a list of all keys.

    int size();  // Returns number of keys.
}
