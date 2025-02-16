package AST.Nodes;

public abstract class Expression {
    // symbolic differentiation
    public abstract Expression derive(String variable);

    // evaluates function at derivative
    public abstract double evaluate();

    // returns string representation of expression
    public abstract String toString();

    // simplify -- very complex, to be implemented at a later stage
    public abstract Expression simplify();
}
