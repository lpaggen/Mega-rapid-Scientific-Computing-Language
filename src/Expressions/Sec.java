package Expressions;

public class Sec extends Expression {

    private final Expression arg;

    public Sec(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression diff(String variable) {
        // use a chain rule to get x' * secx * tanx
        return new Product(new Product(new Sec(arg), new Tangent(arg)), arg.diff(variable));
    }

    @Override
    public double eval(double... values) {
        // need to implement Zero division error thing here
        return 1/Math.cos(arg.eval(values));
    }

    @Override
    public String toString() {
        return STR."sec(\{arg.toString()})";
    }
}
