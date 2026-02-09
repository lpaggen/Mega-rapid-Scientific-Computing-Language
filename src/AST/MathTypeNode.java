package AST;

import Semantic.TypeVisitor;

public final class MathTypeNode implements Type {
    public MathTypeNode() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitMathType(this);
    }
}
