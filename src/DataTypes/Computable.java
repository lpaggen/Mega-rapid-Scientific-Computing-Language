package DataTypes;

import Util.Value;

// So, for the language to work with all the Symbols and Matrices, Computable seems necessary
// i will add the rest of the methods needed for all of this to actually make sense
public interface Computable extends Value {
    String toString();
    Computable add(Computable other);
    Computable subtract(Computable other);
    Computable multiply(Computable other);
    Computable divide(Computable other);
}
