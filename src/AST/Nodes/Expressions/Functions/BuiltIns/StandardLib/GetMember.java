package AST.Nodes.Expressions.Functions.BuiltIns.StandardLib;

import AST.Nodes.Conditional.BooleanNode;
import AST.Nodes.DataStructures.*;
import AST.Nodes.Expressions.Expression;
import AST.Nodes.Expressions.Functions.BuiltIns.BuiltInFunctionSymbol;
import Interpreter.Runtime.Environment;
import Interpreter.Tokenizer.TokenKind;

import java.util.List;

public class GetMember extends BuiltInFunctionSymbol {

    public GetMember() {
        super("getMember", TokenKind.VOID); // dynamic type
    }

    @Override
    public Object call(Environment env, List<Object> args) {
        if (args.size() != 2)
            throw new IllegalArgumentException("GetMember(obj, name) requires 2 arguments.");

        Object obj = args.get(0);
        String member = args.get(1).toString();

        // maybe i move all this into the data structure classes themselves later
        // and need to extend this for the other classes like Array etc.
        if (obj instanceof Graph g) {
            // graph should also support getting a node or edge by id
            return switch (member) {
                case "nodes" -> g.getNodes();
                case "edges" -> g.getEdges();
                case "isDirected" -> new BooleanNode(g.isDirected());
                case "isWeighted" -> g.isWeighted();
                case "size" -> g.getNodes().size();
                default -> g.getNodeOrEdgeByID(member);
            };
        }

        if (obj instanceof Node n) {
            return switch (member) {
                case "id" -> n.getId();
                case "weight" -> n.getValue();
                case "degree" -> n.getDegree();
                case "neighbors" -> n.getNeighborsAsArray();
                default -> throw new IllegalArgumentException("Node has no member '" + member + "'");
            };
        }

        if (obj instanceof Edge e) {
            return switch (member) {
                case "from" -> e.getFrom();
                case "to" -> e.getTo();
                case "weight" -> e.getWeight();
                default -> throw new IllegalArgumentException("Edge has no member '" + member + "'");
            };
        }

        if (obj instanceof List<?> list) {
            return switch (member) {
                case "size" -> list.size();
                default -> throw new IllegalArgumentException("List has no member '" + member + "'");
            };
        }

        if (obj == null) {
            throw new IllegalArgumentException("Cannot access member '" + member + "' on null object");
        }

        throw new IllegalArgumentException("Cannot access member '" + member + "' on type " + obj.getClass().getSimpleName());
    }
}
