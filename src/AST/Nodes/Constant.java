package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Constant extends MathExpression {
    public double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public MathExpression derive(String var) {
        return new Constant(0);
    }

    @Override
    public MathExpression substitute(String... args) {
        throw new UnsupportedOperationException("Cannot substitute constants.");
    }
}
