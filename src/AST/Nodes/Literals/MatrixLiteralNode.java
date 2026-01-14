package AST.Nodes.Literals;

import AST.Nodes.Expressions.Expression;

import java.util.List;

public class MatrixLiteralNode extends Expression {
    private final List<List<Expression>> rows;

    public MatrixLiteralNode(List<List<Expression>> rows) {
        this.rows = rows;
    }

    public List<List<Expression>> getRows() {
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
}
