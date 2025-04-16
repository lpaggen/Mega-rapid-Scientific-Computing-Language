package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public abstract class Statement {
    public abstract void execute(LookupTable<String, Token> env);
}
