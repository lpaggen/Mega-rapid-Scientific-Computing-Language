package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Cot extends MathExpression {

    private final MathExpression arg;

    public Cot(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        return new Multiply(new Constant(-1), new Power(new Cosec(arg), new Constant(2)));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Cot(arg.substitute(args));
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return Math.cos((double) arg.evaluate(env)) / Math.sin((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "cot(" + arg.toString() + ")";
    }
}
