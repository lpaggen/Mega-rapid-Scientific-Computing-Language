package AST.Nodes;

import Interpreter.Runtime.Environment;

// i will see if i even need this at a later stage, maybe it's not needed
// it works without it, but this could make things easier to maintain
public class TrigNode extends Expression {
    private final String functionName;
    private final Expression argument;

    public TrigNode(String functionName, Expression argument) {
        this.functionName = functionName;
        this.argument = argument;
    }

    @Override
    public Object evaluate(Environment env) {
        double argValue = (double) argument.evaluate(env);
        switch (functionName) {
            case "sin":
                return Math.sin(argValue);
            case "cos":
                return Math.cos(argValue);
            case "tan":
                return Math.tan(argValue);
            case "csc":
                return Math.sin(argValue) == 0 ? Double.POSITIVE_INFINITY : 1 / Math.sin(argValue);
            default:
                throw new IllegalArgumentException("Unknown trigonometric function: " + functionName);
        }
    }

    @Override
    public String toString() {
        return functionName + "(" + argument.toString() + ")";
    }
}
