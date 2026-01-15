package AST.Expressions.Functions.BuiltIns.StandardLib;

import AST.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Lexer.TokenKind;
import Runtime.Environment;
import Parser.Symbol;
import Parser.VariableSymbol;

import java.util.List;
import java.util.Map;

public class Vars extends BuiltInFunctionSymbol {
    Environment env;

    public Vars() {
        super("vars", TokenKind.VOID);
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        PrintFunction printFunction = new PrintFunction();
        this.env = env;
        // Call the print function to print all variables in the environment
        Map<String, Symbol> variables = env.getAllSymbols();
        for (Map.Entry<String, Symbol> entry : variables.entrySet()) {
            String varName = entry.getKey();
            Symbol symbol = entry.getValue();
            // Print the variable name and its value
            if (symbol instanceof VariableSymbol vSymbol && vSymbol.getValue() != null) {
                printFunction.call(env, List.of(varName + " = " + vSymbol.getValue() + ", Type: " + vSymbol.getType()));
            } else { // if it's a function: we should print its name, return type, params
                StringBuilder sb = new StringBuilder(varName + " = ");
                sb.append("Function: ").append(symbol.getName()).append(", Type: ").append(symbol.getType());
                if (symbol instanceof VariableSymbol vSymbol) {
                    sb.append(", Value: ").append(vSymbol.getValue());
                }
                printFunction.call(env, List.of(sb.toString()));
            }
        }
        return null; // No return value, just prints the variables
    }
}
