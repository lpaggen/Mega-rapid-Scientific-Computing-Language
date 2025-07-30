package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;
import Interpreter.Tokenizer.TokenKind;

public class PrintFunction extends FunctionNode {

    public PrintFunction() {
        super("print", TokenKind.VOID, null, null); // 'void' is a placeholder for the return type
    }

    // hmm should this really be a string? i don't think so... let's see later on
    // since these return types are hard coded, we should be able to get away with doing this
    public TokenKind getReturnType() {
        return TokenKind.VOID; // print function does not return a value
    }
}
