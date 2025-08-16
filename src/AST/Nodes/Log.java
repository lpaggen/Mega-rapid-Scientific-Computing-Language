package AST.Nodes;

import Util.Environment;

public class Log extends Expression {
    private final Expression arg, base;

    public Log(Expression arg, Expression base) {
        // here we do need the base to check if natural or not
        this.arg = arg;
        this.base = base;
    }

    @Override
    public double evaluate(Environment env) {
        if (base instanceof Exp bExp && bExp.getArg().toString().equals("1")) {
            return Math.log((arg.evaluate(env)));
        } else return Math.log10(arg.evaluate(env));
    }

    @Override
    public String toString() {
        if (base instanceof Exp bExp && bExp.getArg().toString().equals("1")) {
            return "ln(" + arg.toString() + ")";
        } return "log(" + arg.toString() + ")";
    }

    public Expression getArg() {
        return arg;
    }

    public Expression getBase() {
        return base;
    }
}
