package AST.Statements.Functions;

import AST.Expressions.Expression;
import AST.Statements.Statement;

public class ReturnStatementNode extends Statement {
    private final Expression returnValue;

    public ReturnStatementNode(Expression returnValue) {
        this.returnValue = returnValue;
    }

    public Expression getReturnValue() {
        return returnValue;
    }

    @Override
    public String toString() {
        return "ReturnStatementNode{returnValue=" + returnValue + '}';
    }
}
