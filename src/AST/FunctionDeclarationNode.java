package AST;

import AST.Metadata.Functions.ParamNode;
import AST.Visitors.StatementVisitor;

import java.util.List;

public final class FunctionDeclarationNode implements Statement {
    private final String name;
    private final List<ParamNode> parameters;
    private Type returnType;
    private final BraceLiteralNode body;
    public FunctionDeclarationNode(String name, List<ParamNode> parameters, Type returnType, BraceLiteralNode body) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
    }

    FunctionTypeNode getType() {
        return new FunctionTypeNode(
                parameters.stream().map(ParamNode::type).toList(),
                returnType
        );
    }

    public String getName() {
        return name;
    }

    public List<ParamNode> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public BraceLiteralNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionDeclarationNode{name='").append(name).append('\'');
        sb.append(", parameters=[");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i));
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("], returnType=").append(returnType);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitFunctionDeclarationNode(this);
    }
}
