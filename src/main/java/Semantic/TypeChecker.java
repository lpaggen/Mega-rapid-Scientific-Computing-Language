package Semantic;

import AST.*;
import AST.Functions.IntrinsicFunction;
import AST.Metadata.Containers.KnownDimension;
import AST.Metadata.Containers.MatrixShape;

import java.util.List;

public final class TypeChecker implements ExpressionVisitor<TypedExpression>, StatementVisitor<Void> {
    private final Registry registry;
    private final ConstraintStore constraintStore;
    private final SymbolTable symbolTable;
    private final TypeRules typeRules;

    public TypeChecker(Registry registry, ConstraintStore constraintStore, SymbolTable symbolTable, TypeRules typeRules) {
        this.registry = registry;
        this.constraintStore = constraintStore;
        this.symbolTable = symbolTable;
        this.typeRules = typeRules;
    }

    @Override
    public TypedExpression visitBraceLiteral(BraceLiteralNode node) {
        System.out.println("Visiting brace literal with fields: " + node.body());
        return node.accept(this);
    }

    @Override
    public TypedExpression visitBracketLiteral(BracketLiteralNode node) {
        System.out.println("Visiting bracket literal");
        return null;
    }

    @Override
    public TypedExpression visitFunctionCall(FunctionCallNode node) {
        TypedExpression calleeType = node.callee().accept(this);
        IntrinsicFunction fn = registry.lookupNamespaceMethod("intrinsic", node.name());
        return fn.apply(calleeType, constraintStore);
    }

    @Override
    public TypedExpression visitBinaryNode(BinaryNode node) {
        TypedExpression leftType = node.getLeft().accept(this);
        TypedExpression rightType = node.getRight().accept(this);
        Operators operator = node.getOperator();
        Type resultType;
        switch (operator) {
            case MUL -> resultType = typeRules.inferMultiplication(leftType, rightType);
            case DIV -> resultType = typeRules.inferDivision(leftType, rightType);
            case ADD -> resultType = typeRules.inferAddition(leftType, rightType);
            case SUB -> resultType = typeRules.inferSubtraction(leftType, rightType);
            default -> throw new RuntimeException("Unsupported operator: " + operator);
        }
        return new TypedExpression(node, resultType);
    }

    @Override
    public TypedExpression visitIntegerLiteral(IntegerLiteralNode node) {
        return new TypedExpression(node,
                new Type(
                new IntegerTypeInterface(),
                new TypeAttributes(false, true)
                ));
    }

    @Override
    public TypedExpression visitFloatLiteral(FloatLiteralNode node) {
        return new TypedExpression(node,  new Type(
                new FloatTypeInterface(),
                new TypeAttributes(false, true)
        ));
    }

    @Override
    public TypedExpression visitIncrementNode(IncrementNode node) {
        System.out.println("Visiting increment node");
        return null;
    }

    @Override
    public TypedExpression visitVariableNode(VariableNode node) {
        return new TypedExpression(node, symbolTable.lookup(node.name()));
    }

    @Override
    public TypedExpression visitStringLiteral(StringLiteralNode node) {
        System.out.println("Visiting string literal with value: " + node.value());
        return null;
    }

    @Override
    public TypedExpression visitUnaryNode(UnaryNode node) {
        System.out.println("Visiting unary node");
        return null;
    }

    @Override
    public TypedExpression visitPrimaryNode(PrimaryNode node) {
        System.out.println("Visiting primary node");
        return null;
    }

    @Override
    public TypedExpression visitRecordLiteral(RecordLiteralNode node) {
        System.out.println("Visiting record literal with fiel");
        return null;
    }

    @Override
    public TypedExpression visitListLiteral(ListLiteralNode node) {
        System.out.println("visit list literal");
        return null;
    }

    @Override
    public TypedExpression visitBooleanLiteral(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public TypedExpression visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode) {
        MatrixShape shape = inferMatrixShape(matrixLiteralNode);  // actual shape at runtime
        System.out.println("dimensions are " + shape.rows() + " and " + shape.cols());
        TypedExpression elementType = matrixLiteralNode.rows().getFirst().getFirst().accept(this);
        return new TypedExpression(matrixLiteralNode, new Type(
                new MatrixType(elementType.type(), shape.rows(), shape.cols()),
                new TypeAttributes(false, true)
        ));
    }

    @Override
    public TypedExpression visitGroupingNode(GroupingNode groupingNode) {
        System.out.println("Visiting grouping node");
        return null;
    }

    @Override
    public TypedExpression visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode) {
        return null;
    }

    @Override
    public TypedExpression visitSin(Sin sin) {
        return null;
    }

    @Override
    public TypedExpression visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode) {
        return null;
    }

    @Override
    public TypedExpression visitExp(Exp exp) {
        return null;
    }

    @Override
    public TypedExpression visitCsc(Csc csc) {
        return null;
    }

    @Override
    public TypedExpression visitListAccessNode(ListAccessNode listAccessNode) {
        System.out.println("Visiting list access node");
        return null;
    }

    @Override
    public TypedExpression visitMemberAccessNode(MemberAccessNode memberAccessNode) {
        System.out.println("Visiting member access node with member name: " + memberAccessNode.memberName());
        return null;
    }

    @Override
    public TypedExpression visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode) {
        System.out.println("Visiting lambda function node");
        return null;
    }

    @Override
    public TypedExpression visitMapFunctionNode(MapFunctionNode mapFunctionNode) {
        System.out.println("Visiting map function node");
        return null;
    }

    @Override
    public TypedExpression visitAlgebraicSymbol(AlgebraicSymbol algebraicSymbol) {
        System.out.println("Visiting algebraic symbol with name");
        return null;
    }

    @Override
    public TypedExpression visitAlgebraicSymbolLiteral(AlgebraicSymbolLiteralNode algebraicSymbolLiteralNode) {
        System.out.println("Visiting algebraic symbol literal with name");
        return null;
    }

    @Override
    public TypedExpression visitAssignmentNode(AssignmentNode assignmentNode) {
        System.out.println("Visiting assignment node");
        return null;
    }

    @Override
    public TypedExpression visitNameSpaceAccessNode(NamespaceAccessNode node) {
        List<TypedExpression> argTypes = node.args().stream()
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

    @Override
    public Void visitExpressionStatement(ExpressionStatementNode stmt) {
        return null;
    }

    @Override
    public Void visitImportNode(ImportNode importNode) {
        return null;
    }

    @Override
    public Void visitFunctionDeclarationNode(FunctionDeclarationNode functionDeclarationNode) {
        return null;
    }

    @Override
    public Void visitIfNode(IfNode ifNode) {
        return null;
    }

    @Override
    public Void visitReturnStatementNode(ReturnStatementNode returnStatementNode) {
        return null;
    }

    @Override
    public Void visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode) {
        return null;
    }

    @Override
    public Void visitVariableReassignmentNode(VariableReassignmentNode variableReassignmentNode) {
        return null;
    }

    @Override
    public Void visitWhileNode(WhileNode whileNode) {
        return null;
    }

    @Override
    public Void visitClaimStatementNode(ClaimStatementNode claimStatementNode) {
        return null;
    }
}
