package AST;

import AST.Expression;
import AST.Statement;

import java.util.List;

// as of now I don't know if i need other nodes for elif etc, probably not
// but then this will need to be adapted somehow
public final class IfNode implements Statement {
    private final Expression condition; // this is the LogicalBinaryNode that evaluates the condition
    private final List<Statement> thenBlock; // this is the statement that executes if the condition is true
    private final List<Statement> elseBlock; // this is the statement that executes if the condition is false

public IfNode(Expression condition, List<Statement> thenBlock, List<Statement> elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getThenBlock() {
        return thenBlock;
    }

    public List<Statement> getElseBlock() {
        return elseBlock;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IfNode{condition=").append(condition.toString()).append(", thenBlock=[");
        for (int i = 0; i < thenBlock.size(); i++) {
            sb.append(thenBlock.get(i).toString());
            if (i < thenBlock.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], elseBlock=[");
        for (int i = 0; i < elseBlock.size(); i++) {
            sb.append(elseBlock.get(i).toString());
            if (i < elseBlock.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
