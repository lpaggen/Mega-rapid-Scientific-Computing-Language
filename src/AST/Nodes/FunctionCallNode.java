package AST.Nodes;

import Util.Environment;
import Util.FunctionSymbol;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallNode extends Statement {
    private final String functionName;
    // at this stage, we assume that the function is already declared in the environment
    // so arguments must be cast to Expression
    // in declaration time, they are VariableSymbols, now they must inherit Expression
    private final List<Expression> arguments; // we need to keep Expression; eg print(x + y);

    public FunctionCallNode(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public void execute(Environment env) {
        FunctionSymbol function = (FunctionSymbol) env.lookup(functionName);
        List<Expression> evaluatedArgs = new ArrayList<>();
        for (Expression arg : arguments) {
            evaluatedArgs.add(arg.evaluate(env)); // <-- This is crucial!
        }
        function.call(env, evaluatedArgs);
    }
}
