package AST.Nodes.Conditional;

import AST.Nodes.DataTypes.Scalar;
import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class BooleanNode extends Expression {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Expression evaluate(Environment env) {
        return this;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.BOOLEAN;
    }

    public static BooleanNode fromNumeric(Scalar num) {
        return new BooleanNode(num.getDoubleValue() != 0);
    }

    public Scalar toNumeric() {
        return new Scalar(value ? 1 : 0);
    }
}
