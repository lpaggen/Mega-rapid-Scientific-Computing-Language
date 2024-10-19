public class Power extends Expression {

    private final Expression arg, degree;

    public Power(Expression arg, Expression degree) {
        this.arg = arg;
        this.degree = degree;
    }

    @Override
    public Expression diff(String variable) {
        return new Product(
                new Power(arg, new Constant(degree.eval() - 1)),
                new Product(degree, arg.diff(variable))
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
