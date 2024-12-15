package Util;

import java.util.HashMap;
import java.util.Map;

// this class is a new data structure for my language, it's just a triple hash map
// this is necessary because we need to keep track of what is declared and what isn't, as well as their values and types
// also we use a custom "Value" interface for the V field of the table because we need support for diff Objects
public class LookupTable<K, V, T> {

    private Map<K, Entry<V, T>> map = new HashMap<>();

    public V getValue(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).value;
    }

    public T getType(K key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared");
        }
        return map.get(key).type;
    }

    public boolean variableIsDeclared(K key) {
        return map.containsKey(key);
    }

    public void setValue(K key, V value) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' not declared.");
        }
        Entry<V, T> entry = map.get(key);
        entry.value = value; // Update value
    }

    private static class Entry<V, T> {
        private V value;
        private T type;

        public Entry(V value, T type) {
            this.value = value;
            this.type = type;
        }
    }
}
