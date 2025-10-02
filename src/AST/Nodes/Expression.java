package AST.Nodes;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.IntegerConstant;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public abstract class Expression extends ASTNode implements Cloneable {
    // this is getting the variable from the lookup table (which we call env)
    public abstract Expression evaluate(Environment env);
    public TokenKind getType(Environment env) {
        throw new RuntimeException("getType not implemented for " + this.getClass().getSimpleName());
    }

    // will eventually move a lot of the arithmetic to override this
    public Expression add (Expression other, Environment env) {
        throw new RuntimeException("Addition not implemented for " + this.getClass().getSimpleName());
    }

    // this is quite ugly, will refactor later
    public double evaluateNumeric(Environment env) {
        // default implementation, can be overridden by subclasses
        Expression evaluated = evaluate(env);
        if ((evaluated instanceof Constant c) && (c.getType(env) == TokenKind.INTEGER)) {
            IntegerConstant intConst = (IntegerConstant) evaluated;
            return intConst.evaluateInteger();
        } else if ((evaluated instanceof Constant c) && (c.getType(env) == TokenKind.FLOAT)) {
            return c.getDoubleValue();
        } else {
            throw new RuntimeException("Cannot evaluate non-numeric expression: " + evaluated);
        }
    }

    public abstract String toString();

    @Override
    public Expression clone() {
        try {
            return (Expression) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
