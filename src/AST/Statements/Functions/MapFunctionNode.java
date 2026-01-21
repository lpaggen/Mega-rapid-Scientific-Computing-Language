package AST.Statements.Functions;

import AST.Expressions.Expression;
import AST.Statements.Statement;
import AST.Visitors.ExpressionVisitor;

public class MapFunctionNode extends Expression {
    private final Expression body;
    public MapFunctionNode(Expression body) {
        this.body = body;
    }

    public Expression getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "MapFunctionNode{body=" + body + "}";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitMapFunctionNode(this);
    }
}
