package AST.Nodes.Expressions;

import AST.Nodes.ASTNode;

public abstract class Expression extends ASTNode implements Cloneable {
    public abstract String toString();

    @Override
    public Expression clone() {
        try {
            return (Expression) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
