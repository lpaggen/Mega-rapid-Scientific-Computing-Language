package AST;

import Semantic.ExpressionVisitor;
import Lexer.TokenKind;

public record IncrementNode(TokenKind operator, Expression arg) implements Expression {
    // you want to check firstly if it's ++ or --, if not, throw an error
    public IncrementNode {
        if (operator != TokenKind.INCREMENT && operator != TokenKind.DECREMENT) {
            throw new IllegalArgumentException("Operator must be either INCREMENT (++) or DECREMENT (--).");
        }
    }

//    @Override
//    public Expression evaluate(Environment env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar v && v.getValue() instanceof Integer) {
//            Scalar newValue = new Scalar((v.getValue().intValue() + (operator == TokenKind.INCREMENT ? 1 : -1)));
//            env.updateVariable(arg.toString(), new VariableSymbol(arg.toString(), TokenKind.SCALAR, newValue));
//            // now we just want to return a new Constant with the new value
//            return argValue;
//        }
//        throw new RuntimeException("Increment/Decrement operator can only be applied to integer variables.");
//        }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitIncrementNode(this);
    }
}
