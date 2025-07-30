package AST.Nodes;

import Util.Environment;

public class Power extends MathExpression {

    private final MathExpression arg, degree;

    public Power(MathExpression arg, MathExpression degree) {
        this.arg = arg;
        this.degree = degree;
    }

    // there must be a fix here in that the degree is not always numeric, it can be algebraic
    @Override
    public MathExpression derive(String variable) {
        return new Multiply(
                new Power(arg, new Minus(degree, new Constant(-1))), // this has to be Expression
                new Multiply(degree, arg.derive(variable))
        );
    }

    @Override
    public Object evaluate(Environment env) {
        return null;
    }

    @Override
    public String toString() {
        return arg.toString() + "**" + degree.toString();
    }

    @Override
    public MathExpression substitute(String... s) {
        return new Power(arg.substitute(s), degree.substitute(s));
    }
}
