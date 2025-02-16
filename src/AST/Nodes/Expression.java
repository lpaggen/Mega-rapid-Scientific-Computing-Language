package AST.Nodes;

public abstract class Expression {
    // symbolic differentiation
    public abstract Expression derive(String variable);

    public abstract Object evaluate();

    // returns string representation of expression
    public abstract String toString();

    // simplify -- very complex, to be implemented at a later stage
    public abstract Expression simplify();

    // !! THIS METHOD HANDLES THE MATH REPLACEMENT OF VARIABLES
    public abstract Expression substitute(String ... s);
}
