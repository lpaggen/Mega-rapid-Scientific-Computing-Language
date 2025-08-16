package AST.Nodes;

import Util.Environment;

public class MathExpression {
    // i'm gonna go for composition as opposed to OOP for this one
    // this is a base class for all math expressions in the AST.
    private Expression expression;

    public String toString() {
        if (expression == null) {
            return ""; // whatever
        }
        return expression.toString();
    }

    public Expression evaluate(Environment env) {
        if (expression == null) {
            return null; // whatever
        }
        return expression.evaluate(env);
    }



    public MathExpression derive(String var) { // might not really be string, will see
        if (expression == null) {
            return null;
        }
        return expression.derive(var);
    }

    public MathExpression substitute(String... args);
}
