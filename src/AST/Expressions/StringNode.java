//package AST.Expressions;
//
//import Semantic.ScopeStack;
//import Lexer.TokenKind;
//
//public class StringNode extends Expression {
//    private final String value;
//
//    public StringNode(String value) {
//        this.value = value;
//    }
//
//    @Override
//    public Expression evaluate(ScopeStack env) {
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    @Override
//    public TokenKind getType(ScopeStack env) {
//        return TokenKind.STRING;
//    }
//}
