package AST.Nodes;

// the algebraic expression node encompasses all the algebraic expressions
// so it does not cover Cosine etc., but rather the expressions that are inside the Cosine etc.
// Cos(2x - 6y) -- the 2x - 6y is the algebraic expression, not the Cosine
public abstract class AlgebraicExpression extends Expression {
    // returns the derivative of the expression with respect to the variable -- might change to something other than string
    public abstract Expression derive(String variable);

    @Override
    public Object evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

    // this is going to be a bit more involved, so i will work on it later in development
    public Expression simplify() {
        return null;
    }

    public Expression substitute(String... s) {
        return null;
    }

    // i will see which one to use in the end..
    public abstract Expression substitute(Number n);
}
