package DataTypes;

public class Term {
    private final Number coeff;
    private final Symbol symbol;
    private final boolean isFloat;

    public Term(Symbol symbol, Integer coeff) {
        this.symbol = symbol;
        this.coeff = coeff;
        this.isFloat = false;
    }

    public Term(Symbol symbol, Float coeff) {
        this.symbol = symbol;
        this.coeff = coeff;
        this.isFloat = true;
    }

    public Number getCoeff() {
        return this.coeff;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.coeff.toString() + this.getSymbol().toString();
    }
}
