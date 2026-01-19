package Semantic;

public final class TypeSymbol implements Symbol {
    private final String name;

    public TypeSymbol(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
