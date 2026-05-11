package Semantic;

import AST.Metadata.Containers.BinaryDimension;
import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import com.microsoft.z3.*;

public final class ConstraintStore {  // Z3 SMT API
    private final Context ctx = new Context();  // Z3 context for SMT solving
    private final Solver solver = ctx.mkSolver();  // Z3 solver instance

    public void addEqualityConstraint(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.add(ctx.mkEq(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be equal to " + y);
        }
    }

    public void addGreaterThanConstraint(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.add(ctx.mkGt(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be greater than " + y);
        }
    }

    public void addLessThanConstraint(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.add(ctx.mkLt(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be less than " + y);
        }
    }

    public void addGreaterEqualConstraint(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.add(ctx.mkGe(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be greater than or equal to " + y);
        }
    }

    public void addLessEqualConstraint(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.add(ctx.mkLe(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be less than or equal to " + y);
        }
    }

    public boolean impliesEqual(Dimension x, Dimension y) {
        IntExpr a = toIntExpr(x);
        IntExpr b = toIntExpr(y);
        solver.push();
        solver.add(ctx.mkNot(ctx.mkEq(a, b)));
        Status result = solver.check();
        solver.pop();
        return result == Status.UNSATISFIABLE;
    }

    public IntExpr toIntExpr(Dimension d) {  // assumes all is integer, need to enforce this at collection time
        switch (d) {
            case SymbolicDimension(String s) -> {
                return ctx.mkIntConst(s);
            }
            case KnownDimension(int k) -> {
                return ctx.mkInt(k);
            }
            case BinaryDimension binaryDim -> {
                IntExpr leftExpr = toIntExpr(binaryDim.left());
                IntExpr rightExpr = toIntExpr(binaryDim.right());
                return switch (binaryDim.operator()) {
                    case ADD -> (IntExpr) ctx.mkAdd(leftExpr, rightExpr);
                    case SUB -> (IntExpr) ctx.mkSub(leftExpr, rightExpr);
                    case MUL -> (IntExpr) ctx.mkMul(leftExpr, rightExpr);
                    case DIV -> (IntExpr) ctx.mkDiv(leftExpr, rightExpr);
                    default -> throw new IllegalStateException("Unexpected operator: " + binaryDim.operator()
                    );
                };
            }
            case null, default -> throw new RuntimeException("Unknown dimension type: " + d);
        }
    }

    // TODO z3 can simplify expressions too, must check the documentation


    public boolean isSatisfied() {
        return solver.check() == Status.SATISFIABLE;
    }
}
