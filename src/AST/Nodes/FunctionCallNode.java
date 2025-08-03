package AST.Nodes;

import Util.Environment;
import Util.FunctionSymbol;
import Util.Symbol;

import java.util.List;

public class FunctionCallNode extends Statement {
    private final String functionName;
    private final List<Expression> arguments;

    public FunctionCallNode(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void execute(Environment env) {
        Symbol symbol = env.lookup(functionName);

        if (!(symbol instanceof FunctionSymbol functionSymbol)) {
            throw new RuntimeException("Symbol '" + functionName + "' is not a function.");
        }

        // evaluate all argument expressions
        List<Object> evaluatedArgs = arguments.stream()
                .map(arg -> arg.evaluate(env))
                .toList();

        // unified call (either built-in or user-defined)
        functionSymbol.call(env, evaluatedArgs);
    }
}
