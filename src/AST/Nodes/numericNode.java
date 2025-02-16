package AST.Nodes;

public class numericNode extends Expression {
    private final double value;

    public numericNode(float value) {
        this.value = value;
    }

    public numericNode(int value) {
        this.value = value;
    }

    @Override
    public Expression derive(String variable) {
        // constant evaluates to 0
        return new numericNode(0);
    }

    @Override
    public double evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public Expression simplify() {
        return null;
    }

    @Override
    public Expression substitute(String... s) {
        return null;
    }
}
