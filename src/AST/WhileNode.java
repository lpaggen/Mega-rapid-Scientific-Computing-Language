package AST;
import AST.Expression;
import AST.Statement;
import AST.Visitors.StatementVisitor;

import java.util.List;

public final class WhileNode implements Statement {
    private final Expression condition; // this is the condition that is evaluated before each iteration
    private final List<Statement> body; // this is the body of the while loop
    public WhileNode(Expression condition, List<Statement> body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("While loop has no string form.");
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitWhileNode(this);
    }

//    @Override
//    public void execute(Environment env) {
//        if (condition.evaluate(env) instanceof BooleanNode bool) {
//            boolean cond = bool.getValue();
//            while (cond) {
//                env.pushScope();
//                for (Statement stmt : body) {
//                    System.out.println("Executing statement in while loop:");
//                    System.out.println(stmt);
//                    stmt.execute(env);
//                }
//                env.popScope();
//                cond = ((BooleanNode) condition.evaluate(env)).getValue();
//            }
//        }
//    }
}
