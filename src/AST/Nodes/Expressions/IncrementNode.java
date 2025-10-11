package AST.Nodes.Expressions;

import AST.Nodes.DataTypes.Constant;
import AST.Nodes.DataTypes.IntegerConstant;
import Interpreter.Parser.VariableSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

public class IncrementNode extends Expression {
    private final TokenKind operator;
    private final Expression arg;

    // you want to check firstly if it's ++ or --, if not, throw an error
    public IncrementNode(TokenKind operator, Expression arg) {
        if (operator != TokenKind.INCREMENT && operator != TokenKind.DECREMENT) {
            throw new IllegalArgumentException("Operator must be either INCREMENT (++) or DECREMENT (--).");
        }
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public Expression evaluate(Environment env) {
        Expression argValue = arg.evaluate(env);
        if (argValue instanceof Constant v && v.getValue() instanceof Integer) {
            IntegerConstant newValue = new IntegerConstant((Integer) v.getValue() + (operator == TokenKind.INCREMENT ? 1 : -1));
            env.updateVariable(arg.toString(), new VariableSymbol(arg.toString(), TokenKind.INTEGER, newValue));
            // now we just want to return a new Constant with the new value
            return argValue;
        }
        throw new RuntimeException("Increment/Decrement operator can only be applied to integer variables.");
        }

    @Override
    public String toString() {
        return null;
    }
}
