package calc

class CalcEvaluator {

  def evalLiteral(literal: CalcLiteral): Number = {
    literal match {
      case IntLit(value) => value
      case FloatLit(value) => value
    }
  }

  def evalFunctionCall(name: ASTFunctionId, args: List[CalcExpression]): Number = {
    name.value match {
      case "log" => Math.log(this.evalCalcExpression(args.head).doubleValue())
      case _ => throw CalcRuntimeError(s"Unknow function ${name.value}")
    }
  }

  //TODO: re-think about .floatValue() because it transform '5+3' in '8.0' in example.
  def evalBinaryExpression(leftExpression: CalcExpression, operator: CalcOperator, rightExpression: CalcExpression): Number = {
    operator match {
      case Plus => this.evalCalcExpression(leftExpression).floatValue() + this.evalCalcExpression(rightExpression).floatValue()
      case Minus => this.evalCalcExpression(leftExpression).floatValue() - this.evalCalcExpression(rightExpression).floatValue()
      case Mul => this.evalCalcExpression(leftExpression).floatValue() * this.evalCalcExpression(rightExpression).floatValue()
      case Div => this.evalCalcExpression(leftExpression).floatValue() / this.evalCalcExpression(rightExpression).floatValue()
      case Mod => this.evalCalcExpression(leftExpression).floatValue() % this.evalCalcExpression(rightExpression).floatValue()
    }
  }

  def evalUnaryExpression(operator: CalcOperator, expression: CalcExpression): Number = {
    operator match {
      case Minus => -this.evalCalcExpression(expression).floatValue()
      case _ => throw CalcRuntimeError(s"Unknow unary operator $operator")
    }
  }

  def evalCalcExpression(expression: CalcExpression): Number = {
    expression match {
      case literal: CalcLiteral => evalLiteral(literal)
      case FunctionCall(name, args) => evalFunctionCall(name, args)
      case BinaryOperation(leftExpression, operator, rightExpression) => evalBinaryExpression(leftExpression, operator, rightExpression)
      case UnaryOperation(operator, expression) => evalUnaryExpression(operator, expression)
    }
  }

}
