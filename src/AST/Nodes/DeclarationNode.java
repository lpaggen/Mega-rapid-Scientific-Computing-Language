package AST.Nodes;

import Interpreter.Tokenizer.Token;
import Util.LookupTable;

public class DeclarationNode extends Statement {
    private final Token type;
    private final Token variable;
    private final Expression initializer;

    public DeclarationNode(Token type, Token variable, Expression initializer) {
        this.type = type;
        this.variable = variable;
        this.initializer = initializer;
    }

    @Override
    public void execute(LookupTable<String, Token> env) {
        Object value = (initializer != null) ? initializer.evaluate(env) : null;
        env.declareVariable(variable.getLexeme(), new Token(type.getKind(), variable.getLexeme(), value, variable.getLine()));
    }
}
