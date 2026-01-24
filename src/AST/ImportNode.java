package AST;

import AST.Expressions.Functions.BuiltIns.Graphs.GraphsLibrary;
import AST.Expressions.Functions.BuiltIns.Linalg.LinalgLibrary;
import AST.Expressions.Functions.BuiltIns.StandardLib.StandardLibrary;
import AST.Statement;
import AST.Visitors.StatementVisitor;
import Semantic.Symbol;

import java.util.HashMap;

public final class ImportNode implements Statement {
    private final String moduleName;
    private final String alias;

    public ImportNode(String moduleName, String alias) {
        this.moduleName = moduleName;
        this.alias = alias;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getAlias() {
        return alias;
    }

//    @Override
//    public void execute(Environment env) {
//        try {
//            Library libName = Library.valueOf(moduleName.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Module '" + moduleName + "' not found in standard library.", e);
//        }
//        if (alias != null && !alias.isEmpty()) {
//            System.out.println("Importing module '" + moduleName + "' as alias '" + alias + "'.");
//        } else {
//            System.out.println("Importing module '" + moduleName + "'.");
//        }
//
//        Map<String, Symbol> moduleMap = getModuleMap();
//        env.loadModule(moduleMap);
//    }

    private HashMap<String, Symbol> getModuleMap() {
        return switch (moduleName.toLowerCase()) {
            case "stdlib" -> StandardLibrary.builtInSymbols;
            case "linalg" -> LinalgLibrary.LinalgSymbols;
            case "graphs" -> GraphsLibrary.GraphsSymbols;
            default -> throw new IllegalArgumentException("Module '" + moduleName + "' not found in standard library.");
        };
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitImportNode(this);
    }
}
