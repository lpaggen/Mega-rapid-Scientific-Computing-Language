package AST;

import Semantic.TypeVisitor;

public final class MathTypeNodeInterface implements TypeInterface {
    public MathTypeNodeInterface() {}

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitMathType(this);
    }
}
