package AST.Nodes.Expressions.Functions;

import AST.Nodes.Expressions.Expression;
import Interpreter.Runtime.Environment;
import Interpreter.Parser.FunctionSymbol;
import Interpreter.Tokenizer.TokenKind;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallNode extends Expression {
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
        evaluate(env);
    }

    @Override
    public Expression evaluate(Environment env) {
        FunctionSymbol function = (FunctionSymbol) env.lookup(functionName);
        List<Object> evaluatedArgs = new ArrayList<>();
        for (Expression arg : arguments) {
            System.out.println("class of arg: " + arg.getClass().getSimpleName());
            evaluatedArgs.add(arg.evaluate(env));  // <-- This is crucial!
        }
        return (Expression) function.call(env, evaluatedArgs);
    }

    @Override
    public TokenKind getType(Environment env) {
        return env.lookup(functionName).getType();
    }

    @Override
    public String toString() {
        return null;
    }
}
