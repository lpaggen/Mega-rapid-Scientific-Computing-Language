//package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;
//
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import Lexer.TokenKind;
//
//public class Transpose extends BuiltInFunctionSymbol {
//    public Transpose() {
//        super("T", TokenKind.VECTOR);
//    }
//
//    @Override
//    public Object call(Semantic.Environment env, java.util.List<Object> args) {
//        if (args.size() != 1) {
//            throw new IllegalArgumentException("Transpose function requires exactly one argument.");
//        }
//        Object arg = args.getFirst();
//        if (arg instanceof AST.Nodes.DataStructures.Matrix mat) {
//            System.out.println("Transposing a Matrix");
//            return mat.transpose();
//        }
//        throw new IllegalArgumentException("Transpose function requires a Vector-like as an argument.");
//    }
//}
