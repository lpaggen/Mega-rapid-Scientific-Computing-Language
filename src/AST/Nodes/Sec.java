package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Sec extends MathExpression {

    private final MathExpression arg;

    public Sec(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        // use a chain rule to get x' * secx * tanx
        return new Multiply(new Multiply(new Sec(arg), new Tangent(arg)), arg.derive(variable));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Sec((arg).substitute(args));
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return 1 / Math.cos((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "sec(" + arg.toString() + ")";
    }
}
