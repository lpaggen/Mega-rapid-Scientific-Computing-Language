////package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;
////
////import AST.Nodes.Conditional.BooleanNode;
////import AST.Nodes.DataTypes.Constant;
////import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
////import AST.Expressions.StringNode;
////import Runtime.Environment;
////import Lexer.TokenKind;
////
////import java.util.Array;
////
////public class Cast extends BuiltInFunctionSymbol {
////    private final TokenKind toType;
////    private static final String[] supportedTypes = {"int", "string", "bool", "float"};
////
////    public Cast(String arg) {
////        super("cast", TokenKind.VOID); // actual type will be determined dynamically
////        this.toType = switch (arg.toLowerCase()) {
////            case "int" -> TokenKind.INTEGER;
////            case "string" -> TokenKind.STRING;
////            case "bool" -> TokenKind.BOOLEAN;
////            case "float" -> TokenKind.FLOAT;
////            default -> throw new IllegalArgumentException("Unsupported cast type: " + arg);
////        };
////    }
////
////    @Override
////    public Object call(Environment env, Array<Object> args) {
////        if (args.size() != 1) {
////            throw new IllegalArgumentException("Cast function requires exactly one argument.");
////        }
////        Object value = args.get(0);
////        return switch (toType) {
////            case INTEGER -> new Constant(toInt(value));
////            case FLOAT -> new Constant(toFloat(value));
////            case BOOLEAN -> new BooleanNode(toBool(value));
////            case STRING -> new StringNode(toStringValue(value));
////            default -> throw new IllegalArgumentException("Unsupported cast type: " + toType);
////        };
////    }
////
////    private int toInt(Object value) {
////        return Integer.parseInt(value.toString());
////    }
////
////    private float toFloat(Object value) {
////        return Float.parseFloat(value.toString());
////    }
////
////    private boolean toBool(Object value) {
////        String s = value.toString().toLowerCase();
////        if (s.equals("true") || s.equals("false")) {
////            return Boolean.parseBoolean(s);
////        }
////        throw new IllegalArgumentException("Cannot cast value to boolean: " + value);
////    }
////
////    private String toStringValue(Object value) {
////        return value.toString();
////    }
////
////    public TokenKind getToType() {
////        return toType;
////    }
////}
//
//package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;
//
//import AST.Nodes.Conditional.BooleanNode;
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import AST.Expressions.StringNode;
//import Runtime.Environment;
//import Lexer.TokenKind;
//
//import java.util.List;
//
//public class Cast extends BuiltInFunctionSymbol {
//    public Cast() {
//        super("cast", TokenKind.VOID); // will be determined dynamically later
//    }
//
//    private static final String[] supportedTypes = {"int", "string", "bool", "float"};
//
//    @Override
//    public Object call(Environment env, List<Object> args) {
//        if (args.size() != 2) {
//            throw new IllegalArgumentException("Cast function requires exactly two arguments: value and target type.");
//        }
//        Object value = args.getFirst();
//        String targetType = args.get(1).toString().toLowerCase();
//
//        return switch (targetType) {
//            case "int" -> new Scalar(Integer.parseInt(value.toString()));
//            case "float" -> new Scalar(Float.parseFloat(value.toString()));
//            case "bool" -> new BooleanNode(Boolean.parseBoolean(value.toString()));
//            case "str" -> new StringNode(value.toString());  // there's some exceptions here we should handle later
//            default -> throw new IllegalArgumentException("Unsupported cast type: " + targetType + "." +
//                    " Supported types are: " + String.join(", ", supportedTypes) + ".");
//        };
//    }
//}
