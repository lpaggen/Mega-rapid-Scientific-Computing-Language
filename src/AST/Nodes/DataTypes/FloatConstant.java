package AST.Nodes.DataTypes;

import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class FloatConstant extends Constant {
    public FloatConstant(double value) {
        super(value);
        TokenKind type = TokenKind.FLOAT;
    }

    public TokenKind getType(Environment env) {
        return TokenKind.FLOAT;
    }
}
