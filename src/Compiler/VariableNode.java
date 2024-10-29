package Compiler;

public class VariableNode extends ASTNode {

    public String name;
    public String type;

    public VariableNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return name + " (" + type + ")";
    }
}
