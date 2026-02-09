package AST;

import Semantic.ExpressionVisitor;

public final class MemberAccessNode implements Expression {
    private final Expression object;
    private final String memberName;

    public MemberAccessNode(Expression object, String memberName) {
        this.object = object;
        this.memberName = memberName;
    }

    public Expression getObject() {
        return object;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public String toString() {
        return object.toString() + "." + memberName;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitMemberAccessNode(this);
    }
}
