package AST.Literals.Abstract;

import AST.Expressions.Expression;
import AST.Statements.Statement;
import AST.Visitors.ExpressionVisitor;

import java.util.List;

public final class BraceBlockNode extends Expression {
    private final List<Statement> body;

    public BraceBlockNode(List<Statement> body) {
        this.body = body;
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "BraceBlockNode{" +
                "body=" + body +
                '}';
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBraceLiteral(this);
    }
}
