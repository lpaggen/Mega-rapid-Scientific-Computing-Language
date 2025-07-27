package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

public abstract class Expression extends ASTNode {
    // this is getting the variable from the lookup table (which we call env)
    public abstract Object evaluate(EnvReWrite env);
    public abstract String toString();
}
