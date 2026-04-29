package AST;

import Semantic.TypeVisitor;

public final class NodeTypeNodeInterface implements TypeInterface {
    public NodeTypeNodeInterface() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitNodeTypeNode(this);
    }
}
