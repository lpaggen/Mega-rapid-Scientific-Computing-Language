//package AST.Nodes.Expressions.Functions.BuiltIns.Linalg;
//
//import AST.Nodes.Conditional.BooleanNode;
//import AST.Nodes.DataStructures.Matrix;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import Semantic.Environment;
//
//import java.util.List;
//
//public class Dot extends BuiltInFunctionSymbol {
//
//    public Dot() {
//        super("dot", null);  // we need a NUMERIC class eventually, to handle all these cases
//    }
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() != 2) {
//            throw new IllegalArgumentException("dot product requires exactly two arguments.");
//        }
//        Object m1 = args.get(0);
//        Object m2 = args.get(1);
//        if (!(m1 instanceof Matrix) || !(m2 instanceof Matrix)) {
//            throw new IllegalArgumentException("dot product requires both arguments to be Matrix-like.");
//        }
////        if (ar1.rows() != ar2.cols()) {
////            throw new IllegalArgumentException("dot product requires both arguments to be the same size.");
////        }
////        // need to determine what is being multiplied, scalar vs vector, vector vs vector, etc.
////        Expression[][] output = new Expression[ar2.rows()][ar1.cols()];
////        for (int x = 0; x < ar1.rows(); x++) {
////            for (int y = 0; y < ar2.cols(); y++) {
////                String a = output[x][y].getClass().getSimpleName();
////                System.out.println(a);
////            }
////        }
//        return new BooleanNode(true);
//    }
//}
