package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

public class groupingNode extends Expression {
    private final Expression expression;

    public groupingNode(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public Object evaluate(Environment<String, Token> env) {
        return expression.evaluate(env);
    }

    // need to double-check my logic here
    public Expression getValue() {
        return expression;
    }
}
