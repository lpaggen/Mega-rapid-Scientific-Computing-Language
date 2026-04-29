package AST;

import Semantic.TypeVisitor;

import java.util.List;

public final class FunctionTypeNodeInterface implements TypeInterface {
    private final List<TypeInterface> parameters;
    private final TypeInterface returnTypeInterface;

    public FunctionTypeNodeInterface(List<TypeInterface> parameters, TypeInterface returnTypeInterface) {
        this.parameters = parameters;
        this.returnTypeInterface = returnTypeInterface;
    }

    public List<TypeInterface> getParameters() {
        return parameters;
    }

    public TypeInterface getReturnType() {
        return returnTypeInterface;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionTypeNodeInterface{parameters=[");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i));
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], returnTypeInterface=").append(returnTypeInterface).append('}');
        return sb.toString();
    }

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitFunctionTypeNode(this);
    }
}
