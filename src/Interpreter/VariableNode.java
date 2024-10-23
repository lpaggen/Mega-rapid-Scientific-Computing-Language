package Interpreter;

public class VariableNode extends ASTNode {

    public String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
