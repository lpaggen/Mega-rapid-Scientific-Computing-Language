package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Sine extends MathExpression {
    private final MathExpression arg;

    public Sine(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        // chain rule in this case -- even if sin(x), can still easily evaluate with parser
        return new Multiply(new Cosine(arg), arg.derive(variable));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Sine((arg).substitute(args));
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return Math.sin((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }
}
