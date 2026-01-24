package AST.Metadata.Containers;


public final class BinaryDimension implements Dimension {
    private final Dimension left;
    private final Dimension right;
    public enum Op { ADD, SUB, MUL, DIV, MOD }  // don't store as String, this is safer
    private final Op operator;

    public BinaryDimension(Dimension left, Dimension right, Op operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Dimension getLeft() {
        return left;
    }

    public Dimension getRight() {
        return right;
    }

    public Op getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
