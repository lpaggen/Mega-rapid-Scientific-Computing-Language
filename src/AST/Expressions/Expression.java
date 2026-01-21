package AST.Expressions;

import AST.ASTNode;
import AST.Visitors.ExpressionVisitor;

public abstract class Expression extends ASTNode implements Cloneable {
    public abstract String toString();
    public abstract <R> R accept(ExpressionVisitor<R> visitor);

    @Override
    public Expression clone() {
        try {
            return (Expression) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
