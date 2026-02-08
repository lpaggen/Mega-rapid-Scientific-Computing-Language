package AST;

import AST.Visitors.TypeVisitor;

public final class AlgebraicSymbolType implements Type {
    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitAlgebraicSymbolType(this);
    }
}
