package Util;

public interface Value { // a Value interface is necessary for the lookup table, this is Java after all
    Object getValue(); // Object type to allow for different types to be stored in the table
    String toString();
    Object evaluate(Object value); // or datatypes.expression -- still thinking about it
}
