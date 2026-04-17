package AST;

import Semantic.ExpressionVisitor;

public record AssignmentNode(String variableName, Expression value) implements Expression {

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitAssignmentNode(this);
    }
}
