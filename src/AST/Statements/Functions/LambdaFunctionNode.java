package AST.Statements.Functions;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

import java.util.List;

public class LambdaFunctionNode extends Expression {
    private final List<ParamNode> parameters;
    private final Expression body;

    public LambdaFunctionNode(List<ParamNode> parameters, Expression body) {
        this.parameters = parameters;
        this.body = body;
    }

    public Expression getBody() {
        return body;
    }

    public List<ParamNode> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "LambdaFunctionNode{body=" + body + "}";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitLambdaFunctionNode(this);
    }
}
