package AST.Nodes;

public class Cosine extends Expression {
    private final Expression arg;

    public Cosine(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        // cosine chain rule
        return new Product(new Numeric(-1), new Product(new Sine(arg), arg.derive(variable)));
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
