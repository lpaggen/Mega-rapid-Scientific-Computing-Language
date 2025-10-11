package AST.Nodes.Expressions;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class StringNode extends Expression {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.STRING;
    }
}
