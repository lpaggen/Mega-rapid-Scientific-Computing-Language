package AST.Nodes;

public class Power extends Expression {

    private final Expression arg, degree;

    public Power(Expression arg, Expression degree) {
        this.arg = arg;
        this.degree = degree;
    }

    @Override
    public Expression derive(String variable) {
        return new Product(
                new Power(arg, new Numeric(degree.evaluate() - 1)),
                new Product(degree, arg.derive(variable))
        );
    }

    @Override
    public double eval(double... values) {
        // need to fix this at some point
        return 0;
    }

    @Override
    public String toString() {
        return STR."\{arg.toString()}**\{degree.toString()}";
    }
}
