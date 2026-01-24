package AST;

import AST.Expression;
import AST.Statement;
import AST.Visitors.StatementVisitor;

// i can't be sure if this should be the final implementation or not
// but it WILL solve the null statement issue in the conditional branching
public final class VariableReassignmentNode implements Statement {
    private final String variableName;
    private final Expression newValue;

    public VariableReassignmentNode(String variableName, Expression newValue) {
        this.variableName = variableName;
        this.newValue = newValue;
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitVariableReassignmentNode(this);
    }
}
