package AST;

import Semantic.TypeVisitor;

public final class AlgebraicSymbolTypeInterface implements TypeInterface {
    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitAlgebraicSymbolType(this);
    }
}
