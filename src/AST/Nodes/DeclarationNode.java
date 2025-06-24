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

    // IMPORTANT
    // this node should be the one catching type mismatches
    // it's super easy really, we check the literal, and the type of the variable
    // if there's a mismatch, we crash the program OR we are nice and tolerate small mistakes, will see!
    @Override
    public void execute(LookupTable<String, Token> env) {
        Object value = (initializer != null) ? initializer.evaluate(env) : null;
        env.setValue(variable.getLexeme(), new Token(type.getKind(), variable.getLexeme(), value, variable.getLine()));
        env.showLookupTable();
    }
}
