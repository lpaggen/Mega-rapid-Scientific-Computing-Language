package AST.Nodes;

public class SymbolNode extends AlgebraicExpression {
    private final String name;

    public SymbolNode(String name) {
        this.name = name;
    }

    // here it's just a matter of checking if the variable is the same as the symbol
    @Override
    public Expression derive(String variable) {
        return this.name.equals(variable) ? new NumericNode(1) : new NumericNode(0);
    }

    @Override
    public Object evaluate() {
        throw new UnsupportedOperationException("Cannot evaluate a symbolic variable directly.");
    }

    @Override
    public String toString() {
        return name;
    }

    // simplify should return the symbol as is
    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression substitute(Number n) {
        if (name.equals())
    }
}
