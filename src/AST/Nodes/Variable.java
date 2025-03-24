package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    // we fetch from the environment
    @Override
    public Object evaluate(LookupTable<String, Token> env) {
        if (env.isDeclared(name)) {
            return env.getLiteral(name);
        }
        throw new RuntimeException("Undefined variable: " + name);
    }
}
