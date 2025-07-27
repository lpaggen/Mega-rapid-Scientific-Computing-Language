package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class Exp extends MathExpression {
    private final MathExpression arg;

    public Exp(MathExpression arg) {
        this.arg = arg;
    }

    @Override
    public MathExpression derive(String variable) {
        // chain rule of e, should be correct
        return new Multiply(arg.derive(variable), new Exp(arg));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Exp(arg.substitute(args));
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        return Math.exp((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        return "exp(" + arg.toString() + ")";
    }
}
