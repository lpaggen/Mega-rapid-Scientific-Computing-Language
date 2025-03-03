package AST.Nodes;

public class Power extends Expression {

    private final Expression arg, degree;

    public Power(Expression arg, Expression degree) {
        this.arg = arg;
        this.degree = degree;
    }

    // there must be a fix here in that the degree is not always numeric, it can be algebraic
    @Override
    public Expression derive(String variable) {
        return new Product(
                new Power(arg, new NumericNode(degree.evaluate() - 1)), // this has to be Expression
                new Product(degree, arg.derive(variable))
        );
    }

    @Override
    public Object evaluate() {
        return null;
    }

    @Override
    public String toString() {
        return STR."\{arg.toString()}**\{degree.toString()}";
    }

    @Override
    public Expression substitute(String... s) {
        return null;
    }
}
