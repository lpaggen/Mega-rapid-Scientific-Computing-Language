package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

public class unaryNode extends Expression {
    private final Token operator;
    private final Expression rhs;

    public unaryNode(Token operator, Expression rhs) {
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public Expression derive(String variable) {
        return null;
    }

    @Override
    public double evaluate() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Expression simplify() {
        return null;
    }
}
