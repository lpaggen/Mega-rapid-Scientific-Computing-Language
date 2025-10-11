package AST.Nodes.Statements;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;

import java.util.List;

// as of now I don't know if i need other nodes for elif etc, probably not
// but then this will need to be adapted somehow
public class IfNode extends Statement {
    private final Expression condition; // this is the LogicalBinaryNode that evaluates the condition
    private final List<Statement> thenBranch; // this is the statement that executes if the condition is true
    private final List<Statement> elseBranch; // this is the statement that executes if the condition is false

public IfNode(Expression condition, List<Statement> thenBranch, List<Statement> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(Environment env) {
        if (condition.evaluate(env) instanceof BooleanNode bool && bool.getValue()) {
            for (Statement stmt : thenBranch) {
                stmt.execute(env);
            }
        } else if (elseBranch != null) {
            for (Statement stmt : elseBranch) {
                stmt.execute(env);
            }
        }
    }
}
