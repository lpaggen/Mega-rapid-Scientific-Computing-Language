//package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;
//
//import AST.Nodes.DataStructures.Matrix;
//import AST.Nodes.DataTypes.Scalar;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import Semantic.ScopeStack;
//import Lexer.TokenKind;
//
//import java.util.List;
//
//public class Identity extends BuiltInFunctionSymbol {
//    public Identity() {
//        super("identity", TokenKind.MATRIX);
//    }
//
//    @Override
//    public Object call(ScopeStack env, List<Object> args) {
//        if (args.size() != 1) {
//            throw new RuntimeException("identity function expects exactly one argument.");
//        }
//        Scalar n;
//        try {
//            n = (Scalar) args.getFirst();
//        } catch (ClassCastException e) {
//            throw new RuntimeException("identity function expects an integer argument.");
//        }
//        if (n.getValue().intValue() <= 0) {
//            throw new RuntimeException("Size of identity matrix must be a positive integer.");
//        }
//        return Matrix.identity(n.getValue().intValue());
//    }
//
//    @Override
//    public String toString() {
//        return "identity";
//    }
//
//    @Override
//    public String help() {
//        return "Creates an identity matrix of given size." +
//               "\n\nUsage: identity(n)" +
//               "\n- n: size of the identity matrix (number of rows and columns)" +
//               "\n\nReturns an n x n identity matrix.";
//    }
//}
