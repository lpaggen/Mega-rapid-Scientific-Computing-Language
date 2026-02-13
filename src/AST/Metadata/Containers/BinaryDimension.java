package AST.Metadata.Containers;


import AST.Operators;

public record BinaryDimension(Dimension left, Dimension right, Operators operator) implements Dimension {
    @Override
    public boolean isKnown() {
        return left.isKnown() && right.isKnown();
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
