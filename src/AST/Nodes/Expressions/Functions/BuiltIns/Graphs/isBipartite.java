package AST.Nodes.Expressions.Functions.BuiltIns.Graphs;

import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenKind;

public class isBipartite extends BuiltInFunctionSymbol {
    public isBipartite() {
        super("isBipartite", TokenKind.BOOLEAN);
    }
}
