package Util;

import Compiler.Tokenizer.TokenKind;
import DataStructures.Matrix;

import java.util.HashMap;
import java.util.Map;

// this class is a new data structure for my language, it's just a triple hash map
// this is necessary because we need to keep track of what is declared and what isn't, as well as their values and types
// also we use a custom "Value" interface for the V field of the table because we need support for diff Objects
// V extends value too, as stated previously we will need a Value field to handle all our different types and structures
public class LookupTable<K, V extends Value, T> {

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
        entry.value = value; // update value
    }

    public void declareVariable(K key, T type) {
        if (map.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + key + "' already declared."); // not sure if we use this, counter-intuitive really
        }
        map.put(key, new Entry<>(null, type)); // can initialize with empty value, which is good
    }

    public void assignValueToLookupTable(K key, Object value, T type) {
        if (!variableIsDeclared(key)) {
            declareVariable(key, type); // declare the variable in case it doesn't exist yet, it does not need a value and can be initialized blank
        }

        V inferredValue = inferValue(value, type);
        setValue(key, inferredValue);
    }

    private V inferValue(Object value, T type) { // some further checks need to be done, i don't know how safe this is
        if (type.equals(TokenKind.INTEGER)) {
            return (V) new IntegerValue((Integer) value);
        } else if (type.equals(TokenKind.FLOAT)) {
            return (V) new FloatValue((Float) value);
        } else if (type.equals(TokenKind.MATRIX)) {
            return (V) new MatrixValue((double[][]) value);
        }
        throw new IllegalArgumentException("Could not infer value of '" + type + "', check for errors");
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
