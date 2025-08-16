package AST.Nodes;

import Util.Environment;

public class Div extends Expression {
    private final Expression num, denom;

    public Div(Expression num, Expression denom) {
        this.num = num;
        this.denom = denom;
    }

    @Override
    public double evaluate(Environment env) {
        double numResult = num.evaluate(env);
        double denomResult = denom.evaluate(env);
        return denomResult != 0 ? numResult / denomResult : Double.NaN; // Handle division by zero
    }

    @Override
    public String toString() {
        return num.toString() + " / " + denom.toString();
    }

    public Expression getNum() {
        return num;
    }

    public Expression getDenom() {
        return denom;
    }
}
