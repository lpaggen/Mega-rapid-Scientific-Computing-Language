package AST.Statements.Functions;

import AST.Literals.Abstract.BraceBlockNode;
import AST.Statements.Statement;
import Types.Functions.FunctionTypeNode;
import Types.TypeNode;

import java.util.List;

public class FunctionDeclNode extends Statement {
    private final String name;
    private final List<ParamNode> parameters;
    private TypeNode returnType;
    private final BraceBlockNode body;
    public FunctionDeclNode(String name, List<ParamNode> parameters, TypeNode returnType, BraceBlockNode body) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
    }

    FunctionTypeNode getType() {
        return new FunctionTypeNode(
                parameters.stream().map(ParamNode::getType).toList(),
                returnType
        );
    }

    public String getName() {
        return name;
    }

    public List<ParamNode> getParameters() {
        return parameters;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    public BraceBlockNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionDeclNode{name='").append(name).append('\'');
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
}
