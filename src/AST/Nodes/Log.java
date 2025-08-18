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
    public Object evaluate(Environment env) {
        Object argValue = arg.evaluate(env);
        if (!(argValue instanceof Number)) {
            throw new RuntimeException("Argument to log must be a number, got: " + argValue.getClass());
        }
        if (base instanceof Exp bExp && bExp.getArg().toString().equals("1")) {
            return Math.log((double) argValue);
        } else return Math.log10((double) argValue) / Math.log10((double) base.evaluate(env));
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
