//package AST.Expressions.Mathematics.Trigonometry;
//
//import AST.Expressions.Expression;
//import Runtime.Environment;
//
//public class Sec extends Expression {
//
//    private final Expression arg;
//
//    public Sec(Expression arg) {
//        this.arg = arg;
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(1 / Math.cos(c.getDoubleValue()));
//        }
//        return new Sec(argValue);
//    }
//
//    public double evaluateNumeric(Environment env) {
//        double argValue = arg.evaluateNumeric(env);
//        return 1 / Math.cos(argValue);
//    }
//
//    @Override
//    public String toString() {
//        return "sec(" + arg.toString() + ")";
//    }
//
//    public Expression getArg() {
//        return arg;
//    }
//}
