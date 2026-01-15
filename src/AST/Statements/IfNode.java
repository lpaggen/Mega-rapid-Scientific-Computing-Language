package AST.Statements;

import AST.Expressions.Expression;
import AST.Statements.Statement;

import java.util.List;

// as of now I don't know if i need other nodes for elif etc, probably not
// but then this will need to be adapted somehow
public class IfNode extends Statement {
    private final Expression condition; // this is the LogicalBinaryNode that evaluates the condition
    private final List<Statement> thenBlock; // this is the statement that executes if the condition is true
    private final List<Statement> elseBlock; // this is the statement that executes if the condition is false

public IfNode(Expression condition, List<Statement> thenBlock, List<Statement> elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

//    @Override
//    public void execute(Environment env) {
//        if (condition.evaluate(env) instanceof BooleanNode bool && bool.getValue()) {
//            for (Statement stmt : thenBranch) {
//                stmt.execute(env);
//            }
//        } else if (elseBranch != null) {
//            for (Statement stmt : elseBranch) {
//                stmt.execute(env);
//            }
//        }
//    }
}
