//package AST.Nodes.Expressions.Mathematics.Trigonometry;
//
//import AST.Nodes.DataTypes.Scalar;
//import AST.Expressions.Expression;
//import Runtime.Environment;
//
//public class Cos extends Expression {
//    private final Expression arg;
//
//    public Cos(Expression arg) {
//        this.arg = arg;
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        Expression argValue = this.arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(Math.cos(c.getDoubleValue()));
//        }
//        return new Cos(argValue);
//    }
//
//    public double evaluateNumeric(Environment env) {
//        double argValue = arg.evaluateNumeric(env);
//        return Math.cos(argValue);
//    }
//
//    @Override
//    public String toString() {
//        return "cos(" + arg.toString() + ")";
//    }
//
//    public Expression getArg() {
//        return arg;
//    }
//}
