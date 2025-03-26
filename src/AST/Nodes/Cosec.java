package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Cosec extends MathExpression {

    private final MathExpression arg;

    public Cosec(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        return new Multiply(new Constant(-1), new Cosec(arg));
    }

    // some work needs to be done here, there's two types of things we can run into
    // either when we substitute we get a double value -> 1 / Math.sin
    // or we get a MathExpression, in which case we can just return a math expression?
    @Override
    public MathExpression substitute(String... args) {
        return new Cosec(arg.substitute(args));
    }

    // quite unsure if this will work, it should, given the right circumstances
    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return 1 / Math.sin((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "csc(" + arg.toString() + ")";
    }
}
