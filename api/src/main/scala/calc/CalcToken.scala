package calc

sealed trait CalcToken
sealed trait NumberToken extends CalcToken
sealed trait CalcOperator extends CalcToken
sealed trait CalcKeyword extends CalcToken
sealed trait CalcFunctionId extends CalcToken

case class FunctionId(value: String) extends CalcFunctionId

case class IntToken(value: Int) extends NumberToken
case class FloatToken(value: Float) extends NumberToken

case object Plus extends CalcOperator
case object Minus extends CalcOperator
case object Comma extends CalcKeyword
case object Mul extends CalcOperator
case object Div extends CalcOperator
case object Mod extends CalcOperator
case object Dot extends CalcKeyword
case object LeftParen extends CalcKeyword
case object RightParen extends CalcKeyword