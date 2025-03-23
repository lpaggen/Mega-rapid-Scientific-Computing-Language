package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public abstract class Expression {
    // this is getting the variable from the lookup table (which we call env)
    public abstract Object evaluate(LookupTable<String, Token> env);
    public abstract String toString();

    public abstract class MathExpression extends Expression {
        public abstract Expression derive();

        // here I just need a way to make sure we allow for subbing multiple values
        // the logic should be easy, just looking into env everytime and replacing everything
        public abstract Expression substitute(String[] variables);
    }
}
