package Util;

import AST.Nodes.*;

public class AlgebraUtils {
    public static Expression simplify(Expression expression) {
        switch (expression.getClass().getSimpleName()) {
            case "Mul":
                Mul multiply = (Mul) expression;
                Expression leftMul = simplify(multiply.getLeft());
                Expression rightMul = simplify(multiply.getRight());

                // if either side is a constant, we can simplify
                if (leftMul instanceof Constant lConst && lConst.getValue() == 1) {
                    return rightMul; // 1 * x = x
                }
                if (rightMul instanceof Constant rConst && rConst.getValue() == 1) {
                    return leftMul; // x * 1 = x
                }

                if (leftMul instanceof Constant lConst && lConst.getValue() == 0) {
                    return new Constant(0); // 0 * x = 0
                }
                if (rightMul instanceof Constant rConst && rConst.getValue() == 0) {
                    return new Constant(0); // x * 0 = 0
                }

                if (leftMul instanceof Constant lConst && lConst.getValue() == -1) {
                    return new Mul(new Constant(-1), rightMul); // -1 * x = -x
                }
                if (rightMul instanceof Constant rConst && rConst.getValue() == -1) {
                    return new Mul(leftMul, new Constant(-1)); // x * -1 = -x
                }

                // now we need to check situations for which we have say, x*x
                if (leftMul instanceof VariableNode lVar && rightMul instanceof VariableNode rVar && lVar.getName().equals(rVar.getName())) {
                    return new Pow(lVar, new Constant(2)); // x * x = x^2
                }

                if (leftMul instanceof Pow lPow && rightMul instanceof VariableNode rVar && lPow.getBase().equals(rVar)) {
                    return new Pow(lPow.getBase(), new Add(lPow.getExponent(), new Constant(1))); // x^n * x = x^(n+1)
                }
                if (leftMul instanceof VariableNode lVar && rightMul instanceof Pow rPow && rPow.getBase().equals(lVar)) {
                    return new Pow(lVar, new Add(rPow.getExponent(), new Constant(1))); // x * x^n = x^(n+1)
                }

                if (leftMul instanceof Pow lPow && rightMul instanceof Pow rPow && lPow.getBase().equals(rPow.getBase())) {
                    return new Pow(lPow.getBase(), new Add(lPow.getExponent(), rPow.getExponent())); // x^n * x^m = x^(n+m)
                }

                // this is the case for anything else, like cos(x) * cos(x), which could be 'cos^2(x)'
                // really we might need more granular logic here, but for now we will just return the multiplication
                // it's fine if we do it later, the logic is set up to handle it
                if (leftMul.equals(rightMul)) {
                    return new Pow(leftMul, new Constant(2)); // x * x = x^2 -- fallback case
                }

                return new Mul(leftMul, rightMul);

            case "Add":
                Add add = (Add) expression;
                Expression leftAdd = simplify(add.getLeft());
                Expression rightAdd = simplify(add.getRight());

                // if either side is a constant, we can simplify
                if (leftAdd instanceof Constant lConst && lConst.getValue() == 0) {
                    return rightAdd; // 0 + x = x
                }
                if (rightAdd instanceof Constant rConst && rConst.getValue() == 0) {
                    return leftAdd; // x + 0 = x
                }

                // handle the case where both sides are the same variable
                if (leftAdd instanceof VariableNode lVar && rightAdd instanceof VariableNode rVar && lVar.getName().equals(rVar.getName())) {
                    return new Mul(new Constant(2), lVar); // x + x = 2x
                }

                if (leftAdd.equals(rightAdd)) {
                    return new Mul(new Constant(2), leftAdd); // cos(x) + cos(x) = 2cos(x) -- fallback case
                }

                // pythagoras identity
                if (leftAdd instanceof Pow lPow && lPow.getBase() instanceof Tan lTan && rightAdd instanceof Constant rConst && rConst.getValue() == 1) {
                    return new Pow(new Sec(lTan.getArg()), new Constant(2)); // tan2(x) + 1 = sec^2(x)
                }
                if (leftAdd instanceof Pow lPow && lPow.getBase() instanceof Cot lCot && rightAdd instanceof Constant rConst && rConst.getValue() == 1) {
                    return new Pow(new Csc(lCot.getArg()), new Constant(2)); // cot2(x) + 1 = csc^2(x)
                }

                // cos2x + sin2x = 1
                if (leftAdd instanceof Pow lPow && lPow.getBase() instanceof Cos lCos && rightAdd instanceof Pow rPow && rPow.getBase() instanceof Sin rSin && lCos.getArg().equals(rSin.getArg())) {
                    return new Constant(1); // cos(x) + sin(x) = 1
                }
                if (leftAdd instanceof Pow lPow && lPow.getBase() instanceof Sin lSin && rightAdd instanceof Pow rPow && rPow.getBase() instanceof Cos rCos && lSin.getArg().equals(rCos.getArg())) {
                    return new Constant(1); // sin(x) + cos(x) = 1
                }

                // there is like tons more to add, this is of course gonna take a little while...

                // not able to simplify further, return the addition
                return new Add(leftAdd, rightAdd);

            case "Div":
                // we can simplify division by constants
                Div divide = (Div) expression;
                Expression numerator = simplify(divide.getNum());
                Expression denominator = simplify(divide.getDenom());

                if (denominator instanceof Constant dConst && dConst.getValue() == 1) {
                    return numerator; // x / 1 = x
                }

                if (denominator instanceof Constant dConst && dConst.getValue() == 0) {
                    return new Constant(Double.NaN); // x / 0 = NaN
                }

                if (numerator instanceof Constant nConst && nConst.getValue() == 0) {
                    return new Constant(0); // 0 / x = 0
                }

                if (numerator instanceof Constant nConst && nConst.getValue() == 1 && denominator instanceof VariableNode dVar) {
                    return new Pow(dVar, new Constant(-1)); // 1 / x = x^(-1)
                }

                if (numerator instanceof VariableNode nVar && denominator instanceof Constant dConst && dConst.getValue() == -1) {
                    return new Mul(new Constant(-1), nVar); // x / -1 = -x
                }

                if (numerator instanceof VariableNode nVar && denominator instanceof VariableNode dVar && nVar.getName().equals(dVar.getName())) {
                    return new Constant(1); // x / x = 1
                }

                // this is the case for everything with exponents, like x^4 / x^1 will work too!
                if (numerator instanceof Pow nPow && denominator instanceof Pow dPow && nPow.getBase().equals(dPow.getBase())) {
                    return new Pow(nPow.getBase(), new Sub(nPow.getExponent(), dPow.getExponent())); // x^n / x^m = x^(n-m)
                }

                if (numerator instanceof Pow nPow && denominator instanceof VariableNode dVar && nPow.getBase().equals(dVar)) {
                    return new Pow(nPow.getBase(), new Sub(nPow.getExponent(), new Constant(1))); // x^n / x = x^(n-1)
                }

                if (numerator instanceof VariableNode nVar && denominator instanceof Pow dPow && dPow.getBase().equals(nVar)) {
                    return new Pow(nVar, new Sub(dPow.getExponent(), new Constant(1))); // x / x^n = x^(1-n)
                }

                // some special trigonometric identities!!
                if (numerator instanceof Sin nSin && denominator instanceof Cos dCos) {
                    return new Tan(nSin.getArg()); // sin(x) / cos(x) = tan(x)
                }

                if (numerator instanceof Cos nCos && denominator instanceof Sin dSin) {
                    return new Cot(nCos.getArg()); // cos(x) / sin(x) = cot(x)
                }

                // simplify to sec
                if (numerator instanceof Constant nConst && nConst.getValue() == 1 && denominator instanceof Cos dCos) {
                    return new Sec(dCos.getArg()); // 1 / cos(x) = sec(x)
                }

                // simplify to csc
                if (numerator instanceof Constant nConst && nConst.getValue() == 1 && denominator instanceof Sin dSin) {
                    return new Csc(dSin.getArg()); // 1 / sin(x) = csc(x)
                }

                // sec / csc to tan
                if (numerator instanceof Sec nSec && denominator instanceof Csc dCsc) {
                    return new Tan(nSec.getArg()); // sec(x) / csc(x) = tan(x)
                }

                // csc / sec to cot
                if (numerator instanceof Csc nCsc && denominator instanceof Sec dSec) {
                    return new Cot(nCsc.getArg()); // csc(x) / sec(x) = cot(x)
                }

                // tan / cot tan**2
                if (numerator instanceof Tan nTan && denominator instanceof Cot dCot) {
                    return new Pow(nTan.getArg(), new Constant(2)); // tan(x) / cot(x) = tan^2(x)
                }

                // cot / tan cot**2
                if (numerator instanceof Cot nCot && denominator instanceof Tan dTan) {
                    return new Pow(nCot.getArg(), new Constant(2)); // cot(x) / tan(x) = cot^2(x)
                }

                // tan / sec sin
                if (numerator instanceof Tan nTan && denominator instanceof Sec dSec) {
                    return new Sin(nTan.getArg()); // tan(x) / sec(x) = sin(x)
                }

                // cot / csc cos
                if (numerator instanceof Cot nCot && denominator instanceof Csc dCsc) {
                    return new Cos(nCot.getArg()); // cot(x) / csc(x) = cos(x)
                }

                // cot / sec cos**2/sin
                if (numerator instanceof Cot nCot && denominator instanceof Sec dSec) {
                    return new Div(new Pow(nCot.getArg(), new Constant(2)), new Sin(nCot.getArg())); // cot(x) / sec(x) = cos^2(x) / sin(x)
                }

                // tan / csc sin**2/cos
                if (numerator instanceof Tan nTan && denominator instanceof Csc dCsc) {
                    return new Div(new Pow(nTan.getArg(), new Constant(2)), new Cos(nTan.getArg())); // tan(x) / csc(x) = sin^2(x) / cos(x)
                }

                // this is the case for anything else, like cos(x) / cos(x), which could be '1'
                if (numerator.equals(denominator)) {
                    return new Constant(1); // cos(x) / cos(x) = 1 -- fallback case
                }

                // sec / tan = 1/sin
                if (numerator instanceof Sec nSec && denominator instanceof Tan dTan) {
                    return new Csc(nSec.getArg()); // sec(x) / tan(x) = csc(x)
                }

                // csc / cot = sec
                if (numerator instanceof Csc nCsc && denominator instanceof Cot dCot) {
                    return new Sec(nCsc.getArg()); // csc(x) / cot(x) = sec(x)
                }

            case "Log":
                Log log = (Log) expression;
                Expression arg = simplify(log.getArg());
                Expression base = simplify(log.getBase());

                // if the base is 1, we can simplify to 0
                if (base instanceof Constant bConst && bConst.getValue() == 1 && arg instanceof Constant aConst && aConst.getValue() != 1) {
                    throw new UnsupportedOperationException("Logarithm with base 1 is undefined for values other than 1");
                }



                return new Log(arg, base);

            case "Tan":
                Tan tangent = (Tan) expression;
                return new Tan(simplify(tangent.getArg()));
            case "Sec":
                Sec sec = (Sec) expression;
                return new Sec(simplify(sec.getArg()));
            default:
                throw new UnsupportedOperationException("not supported yet: " + expression.getClass().getSimpleName());
        }
    }

    public static Expression transform(Expression expression) {
        switch (expression.getClass().getSimpleName()) {
            case "Tan":
                Tan tan = (Tan) expression;
                // transform tan(x) to sin(x)/cos(x)
                return new Div(new Sin(tan.getArg()), new Cos(tan.getArg()));
            case "Sin":
                Sin sin = (Sin) expression;
                // transform sin(x) to 1/csc(x)
                return new Csc(sin.getArg());
            case "Cot":
                Cot cot = (Cot) expression;
                // transform cot(x) to 1/tan(x)
                return new Div(new Constant(1), new Tan(cot.getArg()));
            case "Csc":
                Csc csc = (Csc) expression;
                // transform csc(x) to 1/sin(x)
                return new Div(new Constant(1), new Sin(csc.getArg()));
            case "Sec":
                Sec sec = (Sec) expression;
                // transform sec(x) to 1/cos(x)
                return new Div(new Constant(1), new Cos(sec.getArg()));
            case "Cos":
                Cos cos = (Cos) expression;
                // transform cos(x) to 1/sec(x)
                return new Sec(cos.getArg());
//            case "Pow":
//                Pow pow = (Pow) expression;
//                // transform x^n to n*log(x)
//                if (pow.getBase() instanceof VariableNode) {
//                    return new Mul(pow.getExponent(), new Log(pow.getBase()));
//                }
//                // for other bases, we might not have a transformation defined
//                return expression; // no transformation needed
            default:
                // For other types of expressions, we might not have a transformation defined
                return expression; // no transformation needed
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
            case "Tan":
                // derivative of tan(x) is sec^2(x)
                return new Mul(new Sec(((Tan) expression).getArg()), new Sec(((Tan) expression).getArg()));
            case "Sec":
                // derivative of sec(x) is sec(x)tan(x)
                return new Mul(new Sec(((Sec) expression).getArg()), new Tan(((Sec) expression).getArg()));
            default:
                // For other types of expressions, we might not have a derivative defined
                return null;
        }
    }
}
