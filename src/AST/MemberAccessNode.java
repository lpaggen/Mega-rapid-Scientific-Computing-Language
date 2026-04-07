package AST;

import Semantic.ExpressionVisitor;

public record MemberAccessNode(Expression object, String memberName) implements Expression {

    @Override
    public String toString() {
        return object.toString() + "." + memberName;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitMemberAccessNode(this);
    }
}
