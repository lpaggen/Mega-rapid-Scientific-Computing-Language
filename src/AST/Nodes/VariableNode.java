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
    public Expression evaluate(Environment env) {
//        // Retrieve the variable value from the environment
//        Symbol symbol = env.lookup(name);
//        if (symbol instanceof VariableSymbol) {
//            return symbol;
//        } else {
//            throw new RuntimeException("Variable " + name + " not found in environment.");
//        }
        return null;
    }

    public String getName() {
        return name;
    }
}
