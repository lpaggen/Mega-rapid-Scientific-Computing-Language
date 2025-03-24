package AST.Nodes;

public class Cosec extends Expression {

    private final Expression arg;

    public Cosec(Expression arg) {
        this.arg = arg;
    }

    @Override
    public Expression derive(String variable) {
        return new Multiply(new Numeric(-1), new Cosec(arg));
    }

    @Override
    public double eval(double... values) {
        // need to check case when div 0 error
        return 1/Math.sin(arg.evaluate(values));
    }

    @Override
    public String toString() {
        return STR."csc(\{arg.toString()})";
    }
}
