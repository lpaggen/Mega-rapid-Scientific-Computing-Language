package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Power extends Expression {

    private final Expression arg, degree;

    public Power(Expression arg, Expression degree) {
        this.arg = arg;
        this.degree = degree;
    }

    // there must be a fix here in that the degree is not always numeric, it can be algebraic
    public Expression derive(String variable) {
        return new Product(
                new Power(arg, new NumericNode(degree.evaluate() - 1)), // this has to be Expression
                new Product(degree, arg.derive(variable))
        );
    }

    public Expression substitute(String variable) {
        return null;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return null;
    }

    @Override
    public String toString() {
        return STR."\{arg.toString()}**\{degree.toString()}";
    }

    public Expression substitute(String... s) {
        return null;
    }
}
