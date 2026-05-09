package Semantic;

import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import com.microsoft.z3.*;

public final class ConstraintStore {  // Z3 SMT API
    private final Context ctx = new Context();  // Z3 context for SMT solving
    private final Solver solver = ctx.mkSolver();  // Z3 solver instance

    public void addEqualityConstraint(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.add(ctx.mkEq(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be equal to " + y);
        }
    }

    public void addGreaterThanConstraint(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.add(ctx.mkGt(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be greater than " + y);
        }
    }

    public void addLessThanConstraint(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.add(ctx.mkLt(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be less than " + y);
        }
    }

    public void addGreaterEqualConstraint(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.add(ctx.mkGe(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be greater than or equal to " + y);
        }
    }

    public void addLessEqualConstraint(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.add(ctx.mkLe(a, b));
        if (solver.check() == Status.UNSATISFIABLE) {
            throw new RuntimeException("Constraint violation: " + x + " cannot be less than or equal to " + y);
        }
    }

    public boolean impliesEqual(Dimension x, Dimension y) {
        IntExpr a = toExpr(x);
        IntExpr b = toExpr(y);
        solver.push();
        solver.add(ctx.mkNot(ctx.mkEq(a, b)));
        Status result = solver.check();
        solver.pop();
        return result == Status.UNSATISFIABLE;
    }

    public IntExpr toExpr(Dimension d) {
        if (d instanceof SymbolicDimension(String s)) {
            return ctx.mkIntConst(s);
        } else if (d instanceof KnownDimension(int k)) {
            return ctx.mkInt(k);
        } else {
            throw new RuntimeException("Unknown dimension type: " + d);
        }
    }

    public boolean isSatisfied() {
        return solver.check() == Status.SATISFIABLE;
    }
}
