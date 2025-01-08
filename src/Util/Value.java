package Util;

// i don't recall what i was trying to get to here, i will keep this in case i figure out why it was so important
public interface Value { // a Value interface is necessary for the lookup table
    Object getValue(); // Object type to allow for different types to be stored in the table
    String toString();
    Object evaluate(Object value); // or datatypes.expression -- still thinking about it
}
