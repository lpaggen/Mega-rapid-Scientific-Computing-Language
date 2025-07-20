package AST.Nodes.BuiltIns;

import AST.Nodes.FunctionNode;

public class PrintFunction extends FunctionNode {

    public PrintFunction() {
        super("print", "void", null, null); // 'void' is a placeholder for the return type
    }

    @Override
    public Object call(Object... args) {
        for (Object arg : args) {
            System.out.print(arg);
        }
        System.out.println();
        return null;
    }
}
