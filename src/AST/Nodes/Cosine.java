package AST.Nodes;

public class Cosine extends MathExpression {
    private final Expression arg;

    public Cosine(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        // cosine chain rule
        return new Multiply(new Numeric(-1), new Multiply(new Sine(arg), arg.derive(variable)));
    }

    @Override
    public double eval(double... values) {
        return Math.cos(arg.evaluate(values));
    }
    @Override
    public String toString() {
        return STR."cos(\{arg.toString()})";
    }
}
