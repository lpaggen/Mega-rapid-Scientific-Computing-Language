package AST.Nodes.DataTypes;

import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class IntegerConstant extends Constant {
    public IntegerConstant(int value) {
        super(value);
        TokenKind type = TokenKind.INTEGER;
    }

    @Override
    public TokenKind getType(Environment env) {
        return TokenKind.INTEGER;
    }

    public int evaluateInteger() {
        return (int) this.getDoubleValue();
    }
}
