//package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;
//
//import AST.BuiltIns.Functions.Expressions.BuiltInFunctionSymbol;
//import AST.Expressions.StringNode;
//import Semantic.Symbol;
//import Lexer.TokenKind;
//
//public class help extends BuiltInFunctionSymbol {
//    public help() {
//        super("help", TokenKind.VOID);
//    }
//
//    @Override
//    public Object call(Semantic.ScopeStack env, java.util.List<Object> args) {
//        if (args.size() != 1) {
//            throw new IllegalArgumentException("help(functionName) requires 1 argument.");
//        }
//
//        Object obj = args.getFirst();
//
//        if (obj instanceof StringNode functionName && !functionName.getValue().isEmpty()) {
//            Symbol symbol = env.lookup(functionName.getValue());
//            if (symbol instanceof BuiltInFunctionSymbol builtInFunction) {
//                return new PrintFunction().call(env, java.util.List.of(new StringNode(builtInFunction.help())));
//            } else {
//                throw new IllegalArgumentException("No built-in function found with the name: " + functionName);
//            }
//        } else {
//            throw new IllegalArgumentException("help requires a string as its argument.");
//        }
//    }
//}
