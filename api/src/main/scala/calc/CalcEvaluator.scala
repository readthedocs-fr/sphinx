package calc

class CalcEvaluator {

  def evalLiteral(literal: CalcLiteral): Number = {
    literal match {
      case IntLit(value) => value
      case FloatLit(value) => value
    }
  }

  def evalFunctionCall(name: ASTFunctionId, args: List[CalcExpression]): Option[Number] = {
    name.value match {
      case "log" => Option(Math.log(this.evalCalcExpression(args.head) match {
        case Some(value) => value.doubleValue()
        case None => return Option.empty
      }))
      case _ => Option.empty
    }
  }

  //TODO: re-think about .floatValue() because it transform '5+3' in '8.0' in example.
  def evalBinaryExpression(leftExpression: CalcExpression, operator: CalcOperator, rightExpression: CalcExpression): Option[Number] = {
    val leftOptLit = this.evalCalcExpression(leftExpression)
    val rightOptLit = this.evalCalcExpression(rightExpression)
    if (leftOptLit.isEmpty || rightOptLit.isEmpty) Option.empty
    val leftLit = leftOptLit.get
    val rightLit = rightOptLit.get
    operator match {
      case Plus => Option(leftLit.floatValue() + rightLit.floatValue())
      case Minus => Option(leftLit.floatValue() - rightLit.floatValue())
      case Mul => Option(leftLit.floatValue() * rightLit.floatValue())
      case Div => Option(leftLit.floatValue() / rightLit.floatValue())
      case Mod => Option(leftLit.floatValue() % rightLit.floatValue())
    }
  }

  def evalUnaryExpression(operator: CalcOperator, expression: CalcExpression): Option[Number] = {
    val optExpr = this.evalCalcExpression(expression)
    if(optExpr.isEmpty) Option.empty
    operator match {
      case Minus => Option(-optExpr.get.floatValue())
      case _ => Option.empty
    }
  }

  def evalCalcExpression(expression: CalcExpression): Option[Number] = {
    expression match {
      case literal: CalcLiteral => Option(evalLiteral(literal))
      case FunctionCall(name, args) => evalFunctionCall(name, args)
      case BinaryOperation(leftExpression, operator, rightExpression) => evalBinaryExpression(leftExpression, operator, rightExpression)
      case UnaryOperation(operator, expression) => evalUnaryExpression(operator, expression)
    }
  }

}
