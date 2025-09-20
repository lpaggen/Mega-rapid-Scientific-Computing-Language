package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataStructures.Array;
import AST.Nodes.Expression;
import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class MatchAny extends BuiltInFunctionSymbol {
    public MatchAny() {
        super("matchAny", TokenKind.BOOLEAN);
    }

    // can make this more efficient if we really want to by immediately asserting
    // can't match type if array doesn't even contain that type, will do later
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
            if (x.equals(toMatch)) {
                return new BooleanNode(true);
            }
        }
        return new BooleanNode(false);
    }
}
