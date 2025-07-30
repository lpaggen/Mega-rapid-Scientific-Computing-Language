package AST.Nodes;

import Util.Environment;

public abstract class MathExpression extends Expression {

    @Override
    public abstract Object evaluate(Environment env);

    @Override
    public abstract String toString();

    public abstract MathExpression derive(String var);

    public abstract MathExpression substitute(String... args);
}
