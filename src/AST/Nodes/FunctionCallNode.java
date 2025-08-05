package AST.Nodes;

import AST.Nodes.BuiltIns.BuiltIns;
import Util.Environment;
import Util.FunctionSymbol;
import Util.Symbol;
import Util.VariableSymbol;

import java.util.Collections;
import java.util.List;

public class FunctionCallNode extends Statement {
    private final String functionName;
    private final List<ASTNode> arguments;

    public FunctionCallNode(String functionName, List<ASTNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void execute(Environment env) {
        // first we'll handle the built-in functions
        FunctionSymbol functionSymbol = (FunctionSymbol) env.lookup(functionName);
        if (BuiltIns.isBuiltInFunction(functionName)) {
            System.out.println("calling execute on the function symbol: " + functionSymbol.getName());
            functionSymbol.call(env, arguments);
        }
    }
}
