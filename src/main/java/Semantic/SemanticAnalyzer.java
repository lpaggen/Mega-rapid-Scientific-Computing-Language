package Semantic;

import AST.Statement;
import AST.TypeRules;

import java.util.List;

public class SemanticAnalyzer {
    private final SymbolTableBuilder symbolTableBuilder;
    private final ConstraintStoreBuilder constraintStoreBuilder;
    private final TypeChecker typeChecker;
    private final ExpressionDispatchVisitor expressionDispatchVisitor;
    private final Registry registry = new Registry();

    public SemanticAnalyzer() {
        SymbolTable symbols = new SymbolTable();
        List<String> errors = new java.util.ArrayList<>();
        ConstraintStore constraintStore = new ConstraintStore();
        TypeRules typeRules = new TypeRules(constraintStore);
        this.symbolTableBuilder = new SymbolTableBuilder(symbols, errors);
        this.constraintStoreBuilder = new ConstraintStoreBuilder(constraintStore, errors);
        this.typeChecker = new TypeChecker(registry, constraintStore, symbols, typeRules);
        this.expressionDispatchVisitor = new ExpressionDispatchVisitor(typeChecker, constraintStore, symbols);
    }

    public void run(List<Statement> ast) {
        symbolTableBuilder.build(ast);
        constraintStoreBuilder.collect(ast);
        expressionDispatchVisitor.visit(ast);

        symbolTableBuilder.printErrors();
    }
}
