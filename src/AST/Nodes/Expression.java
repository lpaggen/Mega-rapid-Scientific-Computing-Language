package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public abstract class Expression {
    // this is getting the variable from the lookup table (which we call env)
    public abstract Object evaluate(LookupTable<String, Token> env);
    public abstract String toString();
}
