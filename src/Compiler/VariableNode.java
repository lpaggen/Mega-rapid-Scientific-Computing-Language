package Compiler;

public class VariableNode extends ASTNode {

    public String name;
    public TokenKind type;

    public VariableNode(String name, TokenKind type) {
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return name + " (" + type + ")";
    }
}
