package AST.Nodes;

public abstract class Expression {
    // symbolic differentiation
    public abstract Expression diff(String variable);

    // evaluates function at derivative
    public abstract double eval(double... values);

    // returns string representation of expression
    public abstract String toString();
}
