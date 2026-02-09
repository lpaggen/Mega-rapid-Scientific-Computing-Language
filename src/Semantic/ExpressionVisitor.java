package Semantic;

import AST.*;
import AST.BinaryNode;
import AST.AlgebraicSymbol;
import AST.ImportNode;
import AST.FunctionCallNode;
import AST.LambdaFunctionNode;
import AST.MapFunctionNode;
import AST.Exp;
import AST.Csc;
import AST.Sin;
import AST.BraceLiteralNode;
import AST.BracketLiteralNode;
import AST.RecordLiteralNode;
import AST.EdgeLiteralNode;
import AST.GraphNodeLiteralNode;
import AST.MatrixLiteralNode;

public sealed interface ExpressionVisitor<T> permits Evaluator {
    T visitBraceLiteral(BraceLiteralNode node);
    T visitBracketLiteral(BracketLiteralNode node);
    T visitFunctionCall(FunctionCallNode node);
    T visitBinaryNode(BinaryNode node);
    T visitIntegerLiteral (IntegerLiteralNode node);
    T visitFloatLiteral (FloatLiteralNode node);
    T visitIncrementNode (IncrementNode node);
    T visitVariableNode (VariableNode node);
    T visitStringLiteral (StringLiteralNode node);
    T visitUnaryNode (UnaryNode node);
    T visitPrimaryNode (PrimaryNode node);
    T visitImportNode (ImportNode node);
    T visitRecordLiteral (RecordLiteralNode node);
    T visitListLiteral (ListLiteralNode node);
    T visitBooleanLiteral (BooleanLiteralNode node);
    T visitMatrixLiteralNode(MatrixLiteralNode matrixLiteralNode);
    T visitGroupingNode(GroupingNode groupingNode);
    T visitGraphNodeLiteralNode(GraphNodeLiteralNode nodeLiteralNode);
    T visitSin(Sin sin);
    T visitEdgeLiteralNode(EdgeLiteralNode edgeLiteralNode);
    T visitExp(Exp exp);
    T visitCsc(Csc csc);
    T visitListAccessNode(ListAccessNode listAccessNode);
    T visitMemberAccessNode(MemberAccessNode memberAccessNode);
    T visitLambdaFunctionNode(LambdaFunctionNode lambdaFunctionNode);
    T visitMapFunctionNode(MapFunctionNode mapFunctionNode);
    T visitAlgebraicSymbol(AlgebraicSymbol algebraicSymbol);
    T visitAlgebraicSymbolLiteral(AlgebraicSymbolLiteralNode algebraicSymbolLiteralNode);
}
