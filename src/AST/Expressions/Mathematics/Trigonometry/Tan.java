//package AST.Expressions.Mathematics.Trigonometry;
//
//import AST.Expressions.Expression;
//import Semantic.ScopeStack;
//
//public class Tan extends Expression {
//    private final Expression arg;
//
//    public Tan(Expression arg) {
//        this.arg = arg;
//    }
//
//    @Override
//    public Expression evaluate(ScopeStack env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            return new Scalar(Math.tan(c.getDoubleValue()));
//        }
//        return new Tan(argValue);
//    }
//
//    public double evaluateNumeric(ScopeStack env) {
//        double argValue = arg.evaluateNumeric(env);
//        return Math.tan(argValue);
//    }
//
//    @Override
//    public String toString() {
//        return "tan(" + arg.toString() + ")";
//    }
//
//    public Expression getArg() {
//        return arg;
//    }
//}
