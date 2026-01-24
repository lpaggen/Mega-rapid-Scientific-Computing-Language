package Types;

public abstract class TypeNode {  // this doesn't need to be Expression at all
    private final String name;

    public TypeNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
