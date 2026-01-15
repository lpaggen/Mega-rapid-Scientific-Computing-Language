package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;

import AST.Nodes.DataStructures.Array;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Runtime.Environment;
import Lexer.TokenKind;

import java.util.List;


// TODO -> make this work for nested structures, maybe not matrix, but on Array yes

public class IndexOf extends BuiltInFunctionSymbol {
    public IndexOf() {
        super("IndexOf", TokenKind.SCALAR);
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
