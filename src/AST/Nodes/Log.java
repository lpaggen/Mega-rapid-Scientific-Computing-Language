package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public class Log extends MathExpression {
    private final MathExpression arg, base;

    public Log(MathExpression arg, MathExpression base) {
        // here we do need the base to check if natural or not
        this.arg = arg;
        this.base = base;
    }

    @Override
    public MathExpression derive(String variable) {
        // need base 2 and any other base sorted
        // actually we only support base 2 and 10 atm, rarely need other values
        if (base.toString().equals("2")) {
            return new Divide(new Constant(1), new Multiply(arg, new Log(arg, base)));
        } else return new Divide(new Constant(1), new Log(arg, base));
    }

    @Override
    public MathExpression substitute(String... args) {
        return new Log(arg.substitute(args), base.substitute(args));
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        if (base.toString().equals("2")) {
            return Math.log((double) arg.evaluate(env));
        } else return Math.log10((double) arg.evaluate(env));
    }

    @Override
    public String toString() {
        if (base.toString().equals("2")) {
            return "ln(" + arg.toString() + ")";
        } return "log(" + arg.toString() + ")";
    }
}
