package Util;

import AST.Nodes.*;
import AST.Nodes.BinaryOperations.Scalar.Add;
import AST.Nodes.BinaryOperations.Scalar.Div;
import AST.Nodes.BinaryOperations.Scalar.Mul;
import AST.Nodes.BinaryOperations.Scalar.Sub;
import AST.Nodes.DataTypes.Constant;

public class Rewrite {
    private static Expression rewrite(Expression expr) {
        if (expr instanceof UnaryNode unaryExpr) {
            Expression arg = rewrite(unaryExpr.getArg());
            return new UnaryNode(unaryExpr.getOperator(), arg);
        } else if (expr instanceof Constant || expr instanceof VariableNode) {
            return expr; // Constants and variables are not rewritten
        }

        // log(x * y) -> log(x) + log(y)
        if (expr instanceof Log logExpr && logExpr.getArg() instanceof Mul mulExpr) {
            return new Add(new Log(mulExpr.getLeft(), logExpr.getBase()),
                           new Log(mulExpr.getRight(), logExpr.getBase()));
        }
        // log(x / y) -> log(x) - log(y)
        if (expr instanceof Log logExpr && logExpr.getArg() instanceof Div divExpr) {
            return new Sub(new Log(divExpr.getNum(), logExpr.getBase()),
                                new Log(divExpr.getDenom(), logExpr.getBase()));
        }
        // log(x^y) -> y * log(x)
        if (expr instanceof Log logExpr && logExpr.getArg() instanceof Pow powExpr) {
            return new Mul(powExpr.getExponent(), new Log(powExpr.getBase(), logExpr.getBase()));
        }
        // sin (x + y) -> sin x cos y + cos x sin y
        if (expr instanceof Add addExpr && addExpr.getLeft() instanceof Sin sinExpr && addExpr.getRight() instanceof Sin sinExpr2) {
            return new Add(new Mul(sinExpr.getArg(), new Cos(sinExpr2.getArg())),
                           new Mul(new Cos(sinExpr.getArg()), sinExpr2.getArg()));
        }
        // sin (x - y) -> sin x cos y - cos x sin y
        if (expr instanceof Sub subtractExpr && subtractExpr.getLeft() instanceof Sin sinExpr && subtractExpr.getRight() instanceof Sin sinExpr2) {
            return new Sub(new Mul(sinExpr.getArg(), new Cos(sinExpr2.getArg())),
                                new Mul(new Cos(sinExpr.getArg()), sinExpr2.getArg()));
        }
        // cos (x + y) -> cos x cos y - sin x sin y
        if (expr instanceof Add addExpr && addExpr.getLeft() instanceof Cos cosExpr && addExpr.getRight() instanceof Cos cosExpr2) {
            return new Sub(new Mul(cosExpr.getArg(), cosExpr2.getArg()),
                                new Mul(new Sin(cosExpr.getArg()), new Sin(cosExpr2.getArg())));
        }
        // cos (x - y) -> cos x cos y + sin x sin y
        if (expr instanceof Sub subtractExpr && subtractExpr.getLeft() instanceof Cos cosExpr && subtractExpr.getRight() instanceof Cos cosExpr2) {
            return new Add(new Mul(cosExpr.getArg(), cosExpr2.getArg()),
                           new Mul(new Sin(cosExpr.getArg()), new Sin(cosExpr2.getArg())));
        }

        // exp(log(x)) -> x
        if (expr instanceof Exp expExpr && expExpr.getArg() instanceof Log logExpr) {
            if (logExpr.getBase() instanceof Exp baseExp && baseExp.getArg().toString().equals("1")) {
                return logExpr.getArg(); // euler logarithm case
            } else {
                return new VariableNode("x"); // Placeholder for non-natural logarithm, idk
            }
        }

        // more cases for other functions as needed
        return expr;
    }
}
