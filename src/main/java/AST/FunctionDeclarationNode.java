package AST;

import AST.Metadata.Functions.ParamNode;
import Semantic.StatementVisitor;

import java.util.List;

public final class FunctionDeclarationNode implements Statement {
    private final String name;
    private final List<ParamNode> parameters;
    private TypeInterface returnTypeInterface;
    private final BraceLiteralNode body;
    private final int line;
    public FunctionDeclarationNode(String name, List<ParamNode> parameters, TypeInterface returnTypeInterface, BraceLiteralNode body, int line) {
        this.name = name;
        this.parameters = parameters;
        this.returnTypeInterface = returnTypeInterface;
        this.body = body;
        this.line = line;
    }

    public int line() {
        return line;
    }

    public FunctionTypeNodeInterface getType() {
        return new FunctionTypeNodeInterface(
                parameters.stream().map(ParamNode::typeInterface).toList(),
                returnTypeInterface
        );
    }

    public String getName() {
        return name;
    }

    public List<ParamNode> getParameters() {
        return parameters;
    }

    public TypeInterface getReturnType() {
        return returnTypeInterface;
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
        sb.append("], returnTypeInterface=").append(returnTypeInterface);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitFunctionDeclarationNode(this);
    }
}
