package AST.Nodes;

import Util.Environment;
import Util.Symbol;
import Util.VariableSymbol;

public class VariableNode extends Expression {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(Environment env) {
        // retrieve the variable value from the environment
        Symbol symbol = env.lookup(name);
        if (symbol instanceof VariableSymbol) {
            return ((VariableSymbol) symbol).getValue();
        } else {
            throw new RuntimeException("Variable " + name + " not found in environment.");
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
