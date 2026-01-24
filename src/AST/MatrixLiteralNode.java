package AST;

import AST.Visitors.ExpressionVisitor;

import java.util.List;

public final class MatrixLiteralNode implements Expression {
    private final List<List<Expression>> rows;

    public MatrixLiteralNode(List<List<Expression>> rows) {
        this.rows = rows;
    }

    public List<List<Expression>> getRows() {
        return rows;
    }

    public List<List<Expression>> getElements() {
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MatrixLiteralNode[\n");
        for (List<Expression> row : rows) {
            sb.append("  [");
            for (Expression e : row) {
                sb.append(e.toString()).append(", ");
            }
            if (!row.isEmpty()) sb.setLength(sb.length() - 2); // remove last comma
            sb.append("]\n");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitMatrixLiteralNode(this);
    }
}
