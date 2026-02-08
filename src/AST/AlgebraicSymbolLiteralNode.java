package AST;

import AST.Visitors.ExpressionVisitor;

public final class AlgebraicSymbolLiteralNode implements Expression {
    @Override
    public String toString() {
        return "AlgebraicSymbolLiteralNode";
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitAlgebraicSymbolLiteral(this);
    }
}
