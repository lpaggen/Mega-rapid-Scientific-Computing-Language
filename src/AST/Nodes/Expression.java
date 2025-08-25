package AST.Nodes;

import Interpreter.Runtime.Environment;

public abstract class Expression extends ASTNode {
    // this is getting the variable from the lookup table (which we call env)
    public abstract Expression evaluate(Environment env);
    public double evaluateNumeric(Environment env) {
        // default implementation, can be overridden by subclasses
        Expression evaluated = evaluate(env);
        if (evaluated instanceof Constant constant) {
            return constant.evaluateNumeric(env);
        } else {
            throw new RuntimeException("Cannot evaluate non-numeric expression: " + evaluated);
        }
    }
    public abstract String toString();
}
