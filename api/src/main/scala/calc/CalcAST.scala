package calc

sealed trait CalcAST
sealed trait CalcExpression extends CalcAST
sealed trait CalcLiteral extends CalcExpression

case class ASTFunctionId(value: String) extends CalcAST

case class FunctionCall(name: ASTFunctionId, args: List[CalcExpression]) extends CalcExpression
case class BinaryOperation(leftExpression: CalcExpression, operator: CalcOperator, rightExpression: CalcExpression) extends CalcExpression
case class UnaryOperation(operator: CalcOperator, expression: CalcExpression) extends CalcExpression

case class IntLit(value: Int) extends CalcLiteral
case class FloatLit(value: Float) extends CalcLiteral
