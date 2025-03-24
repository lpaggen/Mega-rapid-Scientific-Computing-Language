package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public abstract class MathExpression extends Expression {

    @Override
    public abstract Object evaluate(LookupTable<String, Token> env);

    @Override
    public abstract String toString();

    public abstract MathExpression derive(String var);

    public abstract MathExpression substitute(String... args);
}
