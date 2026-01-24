//package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;
//
//import AST.Nodes.DataStructures.Graph;
//import AST.DataStructures.Array;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import Semantic.Environment;
//import Lexer.TokenKind;
//
//import java.util.List;
//import java.util.Set;
//
//public class Get extends BuiltInFunctionSymbol {
//    public Get() {
//        super("get", TokenKind.VOID); // return type depends on the array's type, not sure how to handle it
//    }
//
//    // "get" in my language is just under the hood syntax for []
//    // still you can call it if you want
//    public Object call(Environment env, List<Object> args) {
//        int count = 0;
//        if (args.size() != 2) {  // yes this is counter-intuitive, but you would not use .get directly, always through []
//            throw new IllegalArgumentException("get function requires exactly one argument: the index.");
//        } else if (!getValidTypes().contains(args.getFirst().getClass().getSimpleName())) {
//            throw new IllegalArgumentException("get function requires an Array as the first argument.");
//        } else if (!(args.get(1) instanceof Scalar c && c.getValue() instanceof Integer index)) {
//            throw new IllegalArgumentException("get function requires an integer index as the second argument.");
//        }
//        int index = (Integer) ((Scalar) args.get(1)).getValue();
//        if (index < 0) {
//            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. Index must be non-negative.");
//        }
//
//        // check all types of data structures supported
//        if (args.getFirst() instanceof Array arr) {
//            if (index > arr.length()) {
//                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + arr.length() + ".");
//            }
//            return arr.get(index);
//        }
//        else if (args.getFirst() instanceof Graph) {
//            throw new IllegalArgumentException("get function does not support Graphs. Use GetMember() instead.");
//        }
//        else if (args.getFirst() instanceof Matrix mat) {
//            if (index >= mat.rows()) {
//                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for matrix with " + mat.getRows() + " rows.");
//            }
//            return mat.getRow(index);
//        }
//
////        for (Expression x : array.getElements()) {
////            if (count == index) {
////                return x; // return the element at the specified index
////            }
////            count++;
////        }
//        throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + count + ".");
//    }
//
//    private Set<String> getValidTypes() {
//        return Set.of(
//                "StringNode",
//                "Array",
//                "Graph"
//        ); // VOID for functions that return nothing
//    }
//}
