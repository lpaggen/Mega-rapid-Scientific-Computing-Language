package AST;

import Semantic.ExpressionVisitor;

import java.util.List;

public record NamespaceAccessNode(String namespace, String method, List<VariableNode> args) implements Expression {

    @Override
    public String toString() {
        return namespace + "::" + method + "(" + String.join(", ", args.stream().map(VariableNode::toString).toList()) + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitNameSpaceAccessNode(this);
    }
}
