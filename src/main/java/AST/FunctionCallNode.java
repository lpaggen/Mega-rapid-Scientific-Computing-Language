package AST;

import Semantic.ExpressionVisitor;

import java.util.List;

/**
 * @param callee    we need to keep Expression; eg print(x + y);
 * @param arguments we need to keep Expression; eg print(x + y);
 */
public record FunctionCallNode(String name, Expression callee, List<Expression> arguments) implements Expression {

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i));
            if (i < arguments.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
