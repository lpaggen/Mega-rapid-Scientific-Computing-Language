package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataStructures.Array;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Runtime.Environment;
import Lexer.TokenKind;

public class MatchAll extends BuiltInFunctionSymbol {
    public MatchAll() {
        super("matchAll", TokenKind.BOOLEAN);
    }

    @Override
    public Object call(Environment env, java.util.List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("matchAny function requires exactly two arguments.");
        }
        Object value = args.get(0);
        if (!(value instanceof Array array)) {
            throw new IllegalArgumentException("matchAny function requires a Vector and an Integer as arguments.");
        }
        Object toMatch = args.get(1);
        for (Expression x : array.getElements()) {
            if (!(x.equals(toMatch))) {
                return new BooleanNode(false);
            }
        }
        return new BooleanNode(true);
    }
}
