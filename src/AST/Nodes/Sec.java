package AST.Nodes;

public class Sec extends Expression {

    private final Expression arg;

    public Sec(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        // use a chain rule to get x' * secx * tanx
        return new Multiply(new Multiply(new Sec(arg), new Tangent(arg)), arg.derive(variable));
    }

    @Override
    public double eval(double... values) {
        // need to implement Zero division error thing here
        return 1/Math.cos(arg.evaluate(values));
    }

    @Override
    public String toString() {
        return STR."sec(\{arg.toString()})";
    }
}
