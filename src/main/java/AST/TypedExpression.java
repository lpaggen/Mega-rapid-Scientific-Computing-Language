package AST;

public record TypedExpression(Expression expr, Type type) {
        public TypedExpression {
            if (expr == null) {
                throw new IllegalArgumentException("Expression cannot be null");
            }
            if (type == null) {
                throw new IllegalArgumentException("Type cannot be null");
            }
        }
}
