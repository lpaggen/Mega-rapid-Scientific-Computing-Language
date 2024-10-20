package Expressions;

public class Sine extends Expression {
    private final Expression arg;

    public Sine(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression diff(String variable) {
        // chain rule in this case -- even if sin(x), can still easily evaluate with parser
        return new Product(new Cosine(arg), arg.diff(variable));
    }

    @Override
    public double eval(double... values) {
        // here we need eval etc. because "Math.sin" won't accept a double[]
        return Math.sin(arg.eval(values));
    }

    @Override
    public String toString() {
        return STR."sin(\{arg.toString()})";
    }
}
