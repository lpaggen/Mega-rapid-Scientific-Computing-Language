package AST.Statements;

import AST.Expressions.Expression;

// i can't be sure if this should be the final implementation or not
// but it WILL solve the null statement issue in the conditional branching
public class VariableReassignmentNode extends Statement {
    private final String variableName;
    private final Expression newValue;

    public VariableReassignmentNode(String variableName, Expression newValue) {
        this.variableName = variableName;
        this.newValue = newValue;
    }

//    @Override
//    public void execute(ScopeStack env) {
//        if (!env.isDeclared(variableName)) {
//            throw new RuntimeException("Variable '" + variableName + "' is not declared.");
//        }
//        Expression value = newValue.evaluate(env);
//        TokenKind type = value.getType(env);
//        VariableSymbol varSymbol = new VariableSymbol(variableName, type, value);
//        env.updateVariable(variableName, varSymbol);
//    }
}
