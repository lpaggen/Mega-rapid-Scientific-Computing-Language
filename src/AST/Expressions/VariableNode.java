package AST.Expressions;

import AST.Nodes.ASTNode;
import Interpreter.Tokenizer.TokenKind;

public class VariableNode extends ASTNode {

    private final String name;
    private final TokenKind type;
    private int intValue;
    private float floatValue;

    // need constructor to take values for ALL defined types somehow
    // implement a setValue and getValue method
    // this should help us with the type declaration and keeping track of variable values throughout the code
    public VariableNode(String name, TokenKind type) {
        this.name = name;
        this.type = type;
    }

    public VariableNode(String name, TokenKind type, Integer coeff) {
        this.name = name;
        this.type = type;
        this.intValue = coeff;
    }

    public VariableNode(String name, TokenKind type, Float coeff) {
        this.name = name;
        this.type = type;
        this.floatValue = coeff;
    }

    public void setValue(int value) { // need a special type of whatever, maybe throw in constant then if etc
        // this.value = value;
    }

    public void getValue() { // again need a type return
        // return value;
    }

    public String toString() {
        return type + " (" + name + ")";
    }
}
