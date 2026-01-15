package AST.Nodes.Literals.Abstract;

import AST.Nodes.Expressions.Expression;
import AST.Nodes.Statements.Statement;

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
}
