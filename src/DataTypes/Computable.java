package DataTypes;

// So, for the language to work with all the Symbols and Matrices, Computable seems necessary
// i will add the rest of the methods needed for all of this to actually make sense
public interface Computable<T> extends Value {
    String toString();
    T add(T other);
    T subtract(T other);
    T multiply(T other);
    T divide(T other);
}
