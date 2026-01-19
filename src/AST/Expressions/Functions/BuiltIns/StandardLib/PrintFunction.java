//package AST.Expressions.Functions.BuiltIns.StandardLib;
//
//import AST.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
//import Lexer.TokenKind;
//import Semantic.ScopeStack;
//
//import java.util.List;
//
//public class PrintFunction extends BuiltInFunctionSymbol {
//    public PrintFunction() {
//        super("print", TokenKind.VOID);
//    }
//
//    public Object call(ScopeStack env, List<Object> args) {
//        if (!args.isEmpty() && args.getFirst() != null) {
//            System.out.println(args.getFirst().toString());
//        } else {
//            System.out.println();
//        }
//        return null;
//    }
//}
