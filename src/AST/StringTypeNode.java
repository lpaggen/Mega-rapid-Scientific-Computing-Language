package AST;

import AST.Type;
import AST.Visitors.TypeVisitor;

public final class StringTypeNode implements Type {

    public StringTypeNode() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitStringTypeNode(this);
    }
}
