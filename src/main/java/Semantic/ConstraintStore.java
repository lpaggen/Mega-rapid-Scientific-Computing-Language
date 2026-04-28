package Semantic;

import AST.Metadata.Containers.Dimension;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.SymbolicDimension;

import java.util.HashMap;
import com.microsoft.z3.*;

public final class ConstraintStore {  // Z3 SMT API
    private final Context ctx = new Context();  // Z3 context for SMT solving
    private final Solver solver = ctx.mkSolver();  // Z3 solver instance

    public boolean checkPositivity() {
        //TODO
        return true;
    }

    public void addEqualityConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkEq(z3Var1, z3Var2));
        }
        if (x instanceof KnownDimension(int k1) && y instanceof KnownDimension(int k2)) {
            if (k1 != k2) {
                throw new RuntimeException("Constraint violation: " + k1 + " != " + k2);
            }
        }
        if (x instanceof KnownDimension(int k) && y instanceof SymbolicDimension(String s)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkEq(z3Var, z3Value));
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkEq(z3Var, z3Value));
        }
    }

    public void addGreaterThanConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkGt(z3Var1, z3Var2));
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
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGt(z3Var, z3Value));
        }
    }

    public void addLessThanConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkLt(z3Var1, z3Var2));
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
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLt(z3Var, z3Value));
        }
    }

    public void addGreaterEqualConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkGe(z3Var1, z3Var2));
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
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkGe(z3Var, z3Value));
        }
    }

    public void addLessEqualConstraint(Dimension x, Dimension y) {
        if (x instanceof SymbolicDimension(String s1) && y instanceof SymbolicDimension(String s2)) {
            IntExpr z3Var1 = ctx.mkIntConst(s1);
            IntExpr z3Var2 = ctx.mkIntConst(s2);
            solver.add(ctx.mkLe(z3Var1, z3Var2));
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
        }
        if (x instanceof SymbolicDimension(String s) && y instanceof KnownDimension(int k)) {
            IntExpr z3Var = ctx.mkIntConst(s);
            IntNum z3Value = ctx.mkInt(k);
            solver.add(ctx.mkLe(z3Var, z3Value));
        }
    }

    public Status isSatisfied() {
        return solver.check();
    }
}
