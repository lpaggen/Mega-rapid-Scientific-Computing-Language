package Semantic;

import AST.Statement;

import java.util.List;

public class SemanticAnalyzer {
    private final SymbolTableBuilder symbolTableBuilder;
    private final ConstraintStoreBuilder constraintStoreBuilder;
    private final ExpressionTypeVisitor expressionTypeVisitor;
    private final ExpressionDispatchVisitor expressionDispatchVisitor;
    private final Registry registry = new Registry();

    public SemanticAnalyzer() {
        SymbolTable symbols = new SymbolTable(); // TODO solve, these all should be sharing a common context
        List<String> errors = new java.util.ArrayList<>();
        ConstraintStore constraintStore = new ConstraintStore();
        this.symbolTableBuilder = new SymbolTableBuilder(symbols, errors);
        this.constraintStoreBuilder = new ConstraintStoreBuilder(constraintStore, errors);
        this.expressionTypeVisitor = new ExpressionTypeVisitor(registry, constraintStore, symbols);
        this.expressionDispatchVisitor = new ExpressionDispatchVisitor(expressionTypeVisitor, constraintStore, symbols);
    }

    public void run(List<Statement> ast) {
        symbolTableBuilder.build(ast);
        constraintStoreBuilder.collect(ast);
        expressionDispatchVisitor.visit(ast);

        symbolTableBuilder.printErrors();
    }
}
