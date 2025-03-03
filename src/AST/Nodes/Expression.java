package AST.Nodes;

public abstract class Expression {
    // evaluate should handle all the different types of expressions
    public abstract Object evaluate();

    // returns string representation of expression
    public abstract String toString();
}
