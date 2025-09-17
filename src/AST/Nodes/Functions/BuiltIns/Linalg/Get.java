package AST.Nodes.Functions.BuiltIns.Linalg;

import AST.Nodes.Constant;
import AST.Nodes.DataStructures.ArrayNode;
import AST.Nodes.Expression;
import AST.Nodes.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class Get extends BuiltInFunctionSymbol {
    public Get() {
        super("get", TokenKind.VOID); // return type depends on the array's type, not sure how to handle it
    }

    // "get" in my language is just under the hood syntax for []
    public Object call(Environment env, List<Object> args) {
        int count = 0;
        if (args.size() != 2) {  // yes this is counter-intuitive, but you would never use .get directly, always through []
            throw new IllegalArgumentException("get function requires exactly one argument: the index.");
        } else if (!(args.getFirst() instanceof ArrayNode arrayNode)) {
            throw new IllegalArgumentException("get function requires an ArrayNode as the first argument.");
        } else if (!(args.get(1) instanceof Constant c && c.getValue() instanceof Integer index)) {
            throw new IllegalArgumentException("get function requires an integer index as the second argument.");
        }
        int index = (Integer) ((Constant) args.get(1)).getValue();
        ArrayNode arrayNode = (ArrayNode) args.get(0);
        if (index > arrayNode.getElements().length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + arrayNode.getElements().length + ".");
        }
        for (Expression x : arrayNode.getElements()) {
            count++;
            if (count == index + 1) {  // offset by 1 else MatLab-style indexing, no one wants that
                return x; // return the element at the specified index
            }
        }
        throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + count + ".");
    }
}
