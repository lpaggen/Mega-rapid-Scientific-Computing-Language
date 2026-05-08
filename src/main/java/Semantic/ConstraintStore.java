package Semantic;

import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import com.microsoft.z3.*;

public final class ConstraintStore {  // Z3 SMT API
    private final Context ctx = new Context();  // Z3 context for SMT solving
    private final Solver solver = ctx.mkSolver();  // Z3 solver instance

    public void addEqualityConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkEq(z3Var1, z3Var2));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s1 + " cannot be equal to " + s2);  // TODO stop checking at every constraint, only check at the end, and report all violations together if possible ? is it even possible with z3
            }
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 != k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " != " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            if (k <= 1) {
                throw new RuntimeException("Constraint violation: " + k + " is not greater than 0");
            }
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkEq(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be equal to " + k);
            }
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            if (k <= 1) {
                throw new RuntimeException("Constraint violation: " + k + " is not greater than 0");
            }
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkEq(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be equal to " + k);
            }
        }
    }

    public void addGreaterThanConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkGt(z3Var1, z3Var2));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s1 + " cannot be greater than " + s2);
            }
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 <= k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " is not greater than " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGt(z3Value, z3Var));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be greater than " + k);
            }
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGt(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be greater than " + k);
            }
        }
    }

    public void addLessThanConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkLt(z3Var1, z3Var2));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s1 + " cannot be less than " + s2);
            }
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 >= k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " is not less than " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLt(z3Value, z3Var));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be less than " + k);
            }
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLt(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be less than " + k);
            }
        }
    }

    public void addGreaterEqualConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkGe(z3Var1, z3Var2));
                if (solver.check() == Status.UNSATISFIABLE) {
                    throw new RuntimeException("Constraint violation: " + s1 + " cannot be greater than or equal to " + s2);
                }
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 < k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " is not greater than or equal to " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGe(z3Value, z3Var));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be greater than or equal to " + k);
            }
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGe(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be greater than or equal to " + k);
            }
        }
    }

    public void addLessEqualConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkLe(z3Var1, z3Var2));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s1 + " cannot be less than or equal to " + s2);
            }
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 > k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " is not less than or equal to " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLe(z3Value, z3Var));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be less than or equal to " + k);
            }
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLe(z3Var, z3Value));
            if (solver.check() == Status.UNSATISFIABLE) {
                throw new RuntimeException("Constraint violation: " + s + " cannot be less than or equal to " + k);
            }
        }
    }

    public boolean isSatisfied() {
        return solver.check() == Status.SATISFIABLE;
    }
}
