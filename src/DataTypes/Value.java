package DataTypes;

// this is complicated. the Value interface is necessary for the lookup table, but we also need Computable for the language to work with all the Symbols and Matrices
// i will keep Value for now, it will be useful for the near future, i will remove it if i forget about it and therefore stop using it
public interface Value { // a Value interface is necessary for the lookup table -- we could kind of just use Computable entirely instead, but this would allow us for Strings etc., which are not Computable
    String toString();
}
