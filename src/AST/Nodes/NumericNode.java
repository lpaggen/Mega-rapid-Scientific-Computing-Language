package AST.Nodes;

public class NumericNode extends AlgebraicExpression {
    private final double value;

    public NumericNode(double value) {
        this.value = value;
    }

    @Override
    public Expression derive(String variable) {
        // constant evaluates to 0
        return new NumericNode(0);
    }

    @Override
    public Object evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    // the logic for this is quite involved, so i will work on it later in development
    @Override
    public Expression substitute(String... s) {
        return null;
    }
}
