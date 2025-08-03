package AST.Nodes.BuiltIns;

import AST.Nodes.Statement;
import Util.Environment;
import Util.Symbol;

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
        // In a real interpreter, this would load the module and make its symbols available in the environment.
        // For now, we just print a message indicating the import.
        if (alias != null && !alias.isEmpty()) {
            System.out.println("Importing module '" + moduleName + "' as alias '" + alias + "'.");
        } else {
            System.out.println("Importing module '" + moduleName + "'.");
        }

        // first we'll get the module map from the built-ins
        Map<String, Symbol> moduleMap = BuiltIns.getModuleMap(moduleName);
        env.loadModule(moduleMap);
    }
}
