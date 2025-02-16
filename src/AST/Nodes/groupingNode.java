package AST.Nodes;

public class groupingNode extends Expression {
    private final Expression expression;

    public groupingNode(Expression expression) {
        this.expression = expression;
    }


    @Override
    public Expression derive(String variable) {
        return null;
    }

    @Override
    public double evaluate() {
        return expression.evaluate();
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public Expression simplify() {
        return null;
    }
}
