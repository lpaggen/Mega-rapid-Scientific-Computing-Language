//package AST.Nodes.Expressions.Mathematics.Trigonometry;
//
//import AST.Nodes.DataTypes.Scalar;
//import AST.Expressions.Expression;
//import Runtime.Environment;
//
//public class Cot extends Expression {
//    private final Expression arg;
//
//    public Cot(Expression arg) {
//        this.arg = arg;
//    }
//
//    @Override
//    public Expression evaluate(Environment env) {
//        Expression argValue = arg.evaluate(env);
//        if (argValue instanceof Scalar c) {
//            double value = c.getDoubleValue();
//            if (value == 0) {
//                throw new ArithmeticException("Cotangent is undefined for 0");
//            }
//            return new Scalar(1.0 / Math.tan(value));
//        }
//        return new Cot(argValue);
//    }
//
//    @Override
//    public double evaluateNumeric(Environment env) {
//        double argValue = arg.evaluateNumeric(env);
//        if (argValue == 0) {
//            throw new ArithmeticException("Cotangent is undefined for 0");
//        }
//        return 1.0 / Math.tan(argValue);
//    }
//
//    @Override
//    public String toString() {
//        return "cot(" + arg.toString() + ")";
//    }
//
//    public Expression getArg() {
//        return arg;
//    }
//}
