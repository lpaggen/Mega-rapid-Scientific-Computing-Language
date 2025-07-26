package DataTypes;

import AST.Nodes.MathSymbol;

public class Term {
    private final Number coeff;
    private final MathSymbol symbol;
    private final boolean isFloat;

    public Term(MathSymbol symbol, Integer coeff) {
        this.symbol = symbol;
        this.coeff = coeff;
        this.isFloat = false;
    }

    public Term(MathSymbol symbol, Float coeff) {
        this.symbol = symbol;
        this.coeff = coeff;
        this.isFloat = true;
    }

    public Number getCoeff() {
        return this.coeff;
    }

    public MathSymbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.coeff.toString() + this.getSymbol().toString();
    }
}
