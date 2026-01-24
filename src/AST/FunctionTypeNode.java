package AST;

import AST.Type;
import AST.Visitors.TypeVisitor;

import java.util.List;

public final class FunctionTypeNode implements Type {
    private final List<Type> parameters;
    private final Type returnType;

    public FunctionTypeNode(List<Type> parameters, Type returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
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

    @Override
    public <R> R accept(TypeVisitor<R> visitor) {
        return visitor.visitFunctionTypeNode(this);
    }
}
