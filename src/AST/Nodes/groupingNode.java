package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class groupingNode extends Expression {
    private final Expression expression;

    public groupingNode(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return null;
    }

    // need to double-check my logic here
    public Expression getValue() {
        return expression;
    }
}
