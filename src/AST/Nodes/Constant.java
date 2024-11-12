package AST.Nodes;

public class Constant extends Expression {
    private final double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Expression diff(String variable) {
        // constant evaluates to 0
        return new Constant(0);
    }

    @Override
    public double eval(double... values) {
        // return self
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
