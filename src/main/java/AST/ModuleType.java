package AST;

import Semantic.TypeVisitor;

public record ModuleType(String moduleName) implements Type {

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitModuleType(this);
    }

    @Override
    public String toString() {
        return "Module(" + moduleName + ")";
    }
}
