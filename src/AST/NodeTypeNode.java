package AST;

import AST.Type;
import AST.Visitors.TypeVisitor;

public final class NodeTypeNode implements Type {
    public NodeTypeNode() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitNodeTypeNode(this);
    }
}
