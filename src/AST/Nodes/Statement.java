package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.Environment;

public abstract class Statement extends ASTNode {
    public abstract void execute(Environment<String, Token> env);
}
