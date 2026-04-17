package AST;

import Semantic.ExpressionVisitor;

public final class AlgebraicSymbol implements Expression {
    private final String name;

    public AlgebraicSymbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AlgebraicSymbol(" + name + ")";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitAlgebraicSymbol(this);
    }
}
