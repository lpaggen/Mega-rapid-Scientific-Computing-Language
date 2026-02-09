package AST;

import Semantic.StatementVisitor;

import java.util.List;

/**
 * @param condition this is the LogicalBinaryNode that evaluates the condition
 * @param thenBranch this is the statement that executes if the condition is true
 * @param elseBranch this is the statement that executes if the condition is false
 */ // as of now I don't know if i need other nodes for elif etc, probably not
// but then this will need to be adapted somehow
public record IfNode(Expression condition, List<Statement> thenBranch, List<Statement> elseBranch) implements Statement {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IfNode{condition=").append(condition.toString()).append(", thenBlock=[");
        for (int i = 0; i < thenBranch.size(); i++) {
            sb.append(thenBranch.get(i).toString());
            if (i < thenBranch.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], elseBlock=[");
        for (int i = 0; i < elseBranch.size(); i++) {
            sb.append(elseBranch.get(i).toString());
            if (i < elseBranch.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitIfNode(this);
    }
}
