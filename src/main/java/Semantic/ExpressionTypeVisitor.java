package Semantic;

import AST.*;
import AST.Functions.IntrinsicFunction;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.MatrixShape;

import java.util.List;

public final class ExpressionTypeVisitor implements ExpressionVisitor<Type> {
    private final Registry registry;
    private final ConstraintStore constraintStore;
    private final SymbolTable symbolTable;

    public ExpressionTypeVisitor(Registry registry, ConstraintStore constraintStore, SymbolTable symbolTable) {
        this.registry = registry;
        this.constraintStore = constraintStore;
        this.symbolTable = symbolTable;
    }

    @Override
    public Type visitBraceLiteral(BraceLiteralNode node) {
        System.out.println("Visiting brace literal with fields: " + node.body());
        return node.accept(this);
    }

    @Override
    public Type visitBracketLiteral(BracketLiteralNode node) {
        System.out.println("Visiting bracket literal");
        return null;
    }

    @Override
    public Type visitFunctionCall(FunctionCallNode node) {
        Type calleeType = node.callee().accept(this);
        IntrinsicFunction fn = registry.lookupNamespaceMethod("intrinsic", node.name());
        return fn.apply(calleeType, constraintStore);
    }

    @Override
    public Type visitBinaryNode(BinaryNode node) {
        System.out.println("Visiting binary node with operator");
        return null;
    }

    @Override
    public Type visitIntegerLiteral(IntegerLiteralNode node) {
        return new Type(
                new IntegerTypeInterface(),
                new TypeAttributes(false, true)
        );
    }

    @Override
    public Type visitFloatLiteral(FloatLiteralNode node) {
        return new Type(
                new FloatTypeInterface(),
                new TypeAttributes(false, true)
        );
    }

    @Override
    public Type visitIncrementNode(IncrementNode node) {
        System.out.println("Visiting increment node");
        return null;
    }

    @Override
    public Type visitVariableNode(VariableNode node) {
        System.out.println("Visiting variable node with name: " + node.name());
        return symbolTable.lookup(node.name());
    }

    @Override
    public Type visitStringLiteral(StringLiteralNode node) {
        System.out.println("Visiting string literal with value: " + node.value());
        return null;
    }

    @Override
    public Type visitUnaryNode(UnaryNode node) {
        System.out.println("Visiting unary node");
        return null;
    }

    @Override
    public Type visitPrimaryNode(PrimaryNode node) {
        System.out.println("Visiting primary node");
        return null;
    }

    @Override
    public Type visitRecordLiteral(RecordLiteralNode node) {
        System.out.println("Visiting record literal with fiel");
        return null;
    }

    @Override
    public Type visitListLiteral(ListLiteralNode node) {
        System.out.println("visit list literal");
        return null;
    }

    @Override
    public Type visitBooleanLiteral(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public Type visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode) {
        MatrixShape shape = inferMatrixShape(matrixLiteralNode);  // actual shape at runtime
        System.out.println("dimensions are " + shape.rows() + " and " + shape.cols());
        Type elementType = matrixLiteralNode.rows().getFirst().getFirst().accept(this);
        return new Type(new MatrixTypeNodeInterface(elementType, shape.rows(), shape.cols()), new TypeAttributes(false, false));
    }

    @Override
    public Type visitGroupingNode(GroupingNode groupingNode) {
        System.out.println("Visiting grouping node");
        return null;
    }

    @Override
    public Type visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode) {
        return null;
    }

    @Override
    public Type visitSin(Sin sin) {
        return null;
    }

    @Override
    public Type visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode) {
        return null;
    }

    @Override
    public Type visitExp(Exp exp) {
        return null;
    }

    @Override
    public Type visitCsc(Csc csc) {
        return null;
    }

    @Override
    public Type visitListAccessNode(ListAccessNode listAccessNode) {
        System.out.println("Visiting list access node");
        return null;
    }

    @Override
    public Type visitMemberAccessNode(MemberAccessNode memberAccessNode) {
        System.out.println("Visiting member access node with member name: " + memberAccessNode.memberName());
        return null;
    }

    @Override
    public Type visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode) {
        System.out.println("Visiting lambda function node");
        return null;
    }

    @Override
    public Type visitMapFunctionNode(MapFunctionNode mapFunctionNode) {
        System.out.println("Visiting map function node");
        return null;
    }

    @Override
    public Type visitAlgebraicSymbol(AlgebraicSymbol algebraicSymbol) {
        System.out.println("Visiting algebraic symbol with name");
        return null;
    }

    @Override
    public Type visitAlgebraicSymbolLiteral(AlgebraicSymbolLiteralNode algebraicSymbolLiteralNode) {
        System.out.println("Visiting algebraic symbol literal with name");
        return null;
    }

    @Override
    public Type visitAssignmentNode(AssignmentNode assignmentNode) {
        System.out.println("Visiting assignment node");
        return null;
    }

    @Override
    public Type visitNameSpaceAccessNode(NamespaceAccessNode node) {
        List<Type> argTypes = node.args().stream()
                .map(arg -> arg.accept(this))
                .toList();
        IntrinsicFunction fn = registry.lookupNamespaceMethod(node.namespace(), node.method());  // TODO solve null cases
        return fn.apply(argTypes.getFirst(), constraintStore);
    }

    // this is a helper method to infer the shape of a matrix literal, which can be used in the visitMatrixLiteralNode method
    private MatrixShape inferMatrixShape(MatrixLiteralNode node) {
        int numRows = node.rows().size();
        int numCols = node.rows().getFirst().size();
        for (List<Expression> row : node.rows()) {
            if (row.size() != numCols) {
                throw new RuntimeException("All rows in a matrix literal must have the same number of columns");
            }
        }
        return new MatrixShape(new KnownDimension(numRows), new KnownDimension(numCols));
    }
}
