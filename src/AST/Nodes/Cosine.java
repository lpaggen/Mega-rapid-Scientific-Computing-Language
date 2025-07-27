package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class Cosine extends MathExpression {
    private final MathExpression arg;

    public Cosine(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        // cosine chain rule
        return new Multiply(new Constant(-1), new Multiply(new Sine(arg), arg.derive(variable)));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Cosine(arg.substitute(args));
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        return Math.cos((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "cos(" + arg.toString() + ")";
    }
}
