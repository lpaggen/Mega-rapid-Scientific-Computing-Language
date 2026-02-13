package AST.Metadata.Containers;


public record BinaryDimension(Dimension left, Dimension right, Op operator) implements Dimension {
    @Override
    public boolean isKnown() {
        return left.isKnown() && right.isKnown();
    }

    public enum Op {ADD, SUB, MUL, DIV, MOD}

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
