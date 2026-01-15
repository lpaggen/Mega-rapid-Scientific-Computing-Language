package Types;

import AST.ASTNode;

public abstract class TypeNode extends ASTNode {  // this doesn't need to be Expression at all
    private final String name;

    public TypeNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
