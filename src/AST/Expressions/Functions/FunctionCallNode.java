package AST.Expressions.Functions;

import AST.Expressions.Expression;
import AST.Visitors.ExpressionVisitor;

import java.util.List;

public class FunctionCallNode extends Expression {
    private final Expression callee; // we need to keep Expression; eg print(x + y);
    private final List<Expression> arguments; // we need to keep Expression; eg print(x + y);

    public FunctionCallNode(Expression callee, List<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    public Expression getCallee() {
        return callee;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public String toString() {
        return null;
    }
}
