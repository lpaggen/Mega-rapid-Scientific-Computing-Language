package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.DataStructures.Array;
import AST.Nodes.Expression;
import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class IndexOf extends BuiltInFunctionSymbol {
    public IndexOf() {
        super("IndexOf", TokenKind.INTEGER);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("IndexOf function requires exactly two arguments.");
        }
        if (!(args.get(0) instanceof Array arr && args.get(1) instanceof Expression toFind)) {
            throw new IllegalArgumentException("IndexOf function requires a Vector and an Expression as arguments.");
        }
        int count = 0;
        for (Expression i : arr.getElements()) {
            if (i.equals(toFind)) {
                return count;
            }
            count++;
        }
        return -1;
    }
}
