package AST;

import Semantic.StatementVisitor;

public record VariableDeclarationNode(Type type, String name, Expression initializer, int line) implements Statement {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitVariableDeclarationNode(this);
    }
}
