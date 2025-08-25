package AST.Nodes.Conditional;

import AST.Nodes.Expression;
import AST.Nodes.Statement;
import Interpreter.Runtime.Environment;

// as of now I don't know if i need other nodes for elif etc, probably not
// but then this will need to be adapted somehow
public class IfNode extends Statement {
    private final Expression condition; // this is the LogicalBinaryNode that evaluates the condition
    private final Statement thenBranch; // this is the statement that executes if the condition is true
    private final Statement elseBranch; // this is the statement that executes if the condition is false

public IfNode(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(Environment env) {
        if (condition.evaluate(env) instanceof BooleanNode bool && bool.getValue()) {
            thenBranch.execute(env);
        } else if (elseBranch != null) {
            elseBranch.execute(env);
        }
    }
}
