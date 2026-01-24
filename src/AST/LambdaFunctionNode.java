package AST;

import AST.Metadata.Functions.ParamNode;
import AST.Visitors.ExpressionVisitor;

import java.util.List;

public final class LambdaFunctionNode implements Expression {
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
