package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;
import Util.VariableSymbol;

public abstract class Statement extends ASTNode {
    public abstract void execute(EnvReWrite env);
}
