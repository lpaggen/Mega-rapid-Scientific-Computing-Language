package AST.Nodes.Expressions;

import AST.Nodes.Expressions.Expression;
import Interpreter.Parser.FunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Parser.Symbol;
import Interpreter.Parser.VariableSymbol;
import Interpreter.Tokenizer.TokenKind;

public class VariableNode extends Expression {
    private final String name;
    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Expression evaluate(Environment env) {
        // retrieve the variable value from the environment
        Symbol symbol = env.lookup(name);
        if (symbol instanceof VariableSymbol vs) {
            return vs.getValue();
        } else if (symbol instanceof FunctionSymbol fs) {  // for help()
            return new StringNode(fs.toString());
        }
        throw new RuntimeException("Variable " + name + " not found or not a variable");
    }

    @Override
    public TokenKind getType(Environment env) {
        return env.getType(name);
    }

    @Override
    public double evaluateNumeric(Environment env) {
        return 0; // not sure what this should be atm, will figure it out in later build
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
