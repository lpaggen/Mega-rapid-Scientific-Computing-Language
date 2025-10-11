package AST.Nodes.Expressions.Functions.BuiltIns;

import AST.Nodes.Expressions.Functions.BuiltIns.Linalg.LinalgLibrary;
import AST.Nodes.Expressions.Functions.BuiltIns.StandardLib.StandardLibrary;
import AST.Nodes.Statements.Statement;
import Interpreter.Runtime.Environment;
import Interpreter.Parser.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ImportNode extends Statement {
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

    @Override
    public void execute(Environment env) {
        try {
            Library libName = Library.valueOf(moduleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Module '" + moduleName + "' not found in standard library.", e);
        }
        if (alias != null && !alias.isEmpty()) {
            System.out.println("Importing module '" + moduleName + "' as alias '" + alias + "'.");
        } else {
            System.out.println("Importing module '" + moduleName + "'.");
        }

        Map<String, Symbol> moduleMap = getModuleMap();
        env.loadModule(moduleMap);
    }

    private HashMap<String, Symbol> getModuleMap() {
        return switch (moduleName.toLowerCase()) {
            case "stdlib" -> StandardLibrary.builtInSymbols;
            case "linalg" -> LinalgLibrary.LinalgSymbols;
            default -> throw new IllegalArgumentException("Module '" + moduleName + "' not found in standard library.");
        };
    }
}
