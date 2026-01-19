package Types.Functions;

import AST.Literals.Abstract.BraceBlockNode;
import Types.TypeNode;

import java.util.List;

public final class FunctionTypeNode extends TypeNode {
    private final List<TypeNode> parameters;
    private final TypeNode returnType;

    public FunctionTypeNode(List<TypeNode> parameters, TypeNode returnType) {
        super("Function");
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<TypeNode> getParameters() {
        return parameters;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionTypeNode{parameters=[");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i));
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], returnType=").append(returnType).append('}');
        return sb.toString();
    }
}
