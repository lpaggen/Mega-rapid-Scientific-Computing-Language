package AST.Nodes.BuiltIns;

import java.util.List;

public class PrintFunction extends BuiltInFunction {

    @Override
    public Object call(List<Object> args) {
        if (args.isEmpty()) {
            System.out.println();
        } else {
            for (Object arg : args) {
                System.out.print(arg + " ");
            }
            System.out.println();
        }
        return null;
    }
}
