package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.EnvReWrite;
import Util.Environment;

// the MathSymbol datatype is unique to this language (afaik) - also Sympy uses it
// it allows for algebraic mathematical operations
// as such, 2a + a will return 3a
// !! symbol will handle int and float instead of being a separate data type
public class MathSymbol extends MathExpression {
    private final String name; // name of a symbol can only be

    public MathSymbol(String name) {
        this.name = name;
    }

    private void validateName(String name) { // doesn't need to return anything
        if (!name.matches("[a-z]")) { // check regex for alphabet
            throw new IllegalArgumentException("MathSymbol name must be a single lowercase alphabetical character.");
        }
    }

    @Override
    public Object evaluate(EnvReWrite env) {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public MathExpression derive(String var) {
        if (var.equals(name)) {
            return new Constant(1);
        }
        return new Constant(0);
    }

    // this should be enhanced maybe to support more data types
    @Override
    public MathExpression substitute(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Number of arguments must be 1");
        }
        return new Constant(Integer.parseInt(args[0]));
    }
}
