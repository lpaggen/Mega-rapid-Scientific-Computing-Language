package Util;

import AST.Nodes.*;

public class AlgebraUtils {
    public static Expression simplify(Expression expression) {
        switch (expression.getClass().getSimpleName()) {
            case "Multiply":
                Multiply multiply = (Multiply) expression;
                Expression left = simplify(multiply.getLeft());
                Expression right = simplify(multiply.getRight());

                // if either side is a constant, we can simplify
                if (left instanceof Constant lConst && lConst.getValue() == 1) {
                    return right; // 1 * x = x
                }
                if (right instanceof Constant rConst && rConst.getValue() == 1) {
                    return left; // x * 1 = x
                }

                if (left instanceof Constant lConst && lConst.getValue() == 0) {
                    return new Constant(0); // 0 * x = 0
                }
                if (right instanceof Constant rConst && rConst.getValue() == 0) {
                    return new Constant(0); // x * 0 = 0
                }

                if (left instanceof Constant lConst && lConst.getValue() == -1) {
                    return new Multiply(new Constant(-1), right); // -1 * x = -x
                }
                if (right instanceof Constant rConst && rConst.getValue() == -1) {
                    return new Multiply(left, new Constant(-1)); // x * -1 = -x
                }

                // now we need to check situations for which we have say, x*x
                if (left instanceof VariableNode lVar && right instanceof VariableNode rVar && lVar.getName().equals(rVar.getName())) {
                    return new Power(lVar, new Constant(2)); // x * x = x^2
                }

                if (left instanceof Power lPow && right instanceof VariableNode rVar && lPow.getBase().equals(rVar)) {
                    return new Power(lPow.getBase(), new Add(lPow.getExponent(), new Constant(1))); // x^n * x = x^(n+1)
                }
                if (left instanceof VariableNode lVar && right instanceof Power rPow && rPow.getBase().equals(lVar)) {
                    return new Power(lVar, new Add(rPow.getExponent(), new Constant(1))); // x * x^n = x^(n+1)
                }

                if (left instanceof Power lPow && right instanceof Power rPow && lPow.getBase().equals(rPow.getBase())) {
                    return new Power(lPow.getBase(), new Add(lPow.getExponent(), rPow.getExponent())); // x^n * x^m = x^(n+m)
                }

                return new Multiply(left, right);
            case "Tangent":
                Tangent tangent = (Tangent) expression;
                return new Tangent(simplify(tangent.getArg()));
            case "Sec":
                Sec sec = (Sec) expression;
                return new Sec(simplify(sec.getArg()));
            default:
                throw new UnsupportedOperationException("not supported yet: " + expression.getClass().getSimpleName());
        }
    }

    public static Expression expand(Expression expression) {
        // This is a placeholder for the actual implementation
        // we would need some quite heavy logic here to expand expressions, but we will get it eventually
        // this is probably never going to make it in the language, it's too complex
        return expression;
    }

    public static Expression factor(Expression expression) {
        // This is a placeholder for the actual implementation
        // we would need some quite heavy logic here to factor expressions, but we will get it eventually
        return expression;
    }

    public static Expression differentiate(Expression expression, String variable) {
        // This is a placeholder for the actual implementation
        // we would need some quite heavy logic here to differentiate expressions, but we will get it eventually
        if (expression == null) {
            return null; // whatever
        }
        switch (expression.getClass().getSimpleName()) {
            case "Tangent":
                // derivative of tan(x) is sec^2(x)
                return new Multiply(new Sec(((Tangent) expression).getArg()), new Sec(((Tangent) expression).getArg()));
            case "Sec":
                // derivative of sec(x) is sec(x)tan(x)
                return new Multiply(new Sec(((Sec) expression).getArg()), new Tangent(((Sec) expression).getArg()));
            default:
                // For other types of expressions, we might not have a derivative defined
                return null;
        }
    }
}
