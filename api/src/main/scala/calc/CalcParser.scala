package calc

import scala.util.parsing.combinator.Parsers

class CalcParser extends Parsers {

  override type Elem = CalcToken

  private def parseFunctionId: Parser[ASTFunctionId] = accept("function identifier", { case FunctionId(value) => ASTFunctionId(value) })

  private def parseInteger: Parser[IntLit] = accept("integer literal", { case IntToken(value) => IntLit(value) })

  private def parseFloat: Parser[FloatLit] = accept("float literal", { case FloatToken(value) => FloatLit(value)})

  private def parseLiteral: Parser[CalcLiteral] = parseFloat | parseInteger

  private def parseGrouping: Parser[CalcExpression] = OpeningBracket ~> parseExpression <~ ClosingBracket

  private def parseArgs: Parser[List[CalcExpression]] = OpeningBracket ~> repsep(parseExpression, Comma) <~ ClosingBracket

  private def parseFunctionCall: Parser[FunctionCall] = (parseFunctionId ~ parseArgs) ^^ { case identifier ~ args => FunctionCall(identifier, args)}

  private def parseUnary: Parser[UnaryOperation] = (Minus ~> parseExpression) ^^ (expression => UnaryOperation(Minus, expression))

  private def parseAddition: Parser[(CalcExpression, CalcExpression) => BinaryOperation] = Plus ^^^ { (left, right) => BinaryOperation(left, Plus, right) }
  private def parseSubtraction: Parser[(CalcExpression, CalcExpression) => BinaryOperation] = Minus ^^^ { (left, right) => BinaryOperation(left, Minus, right) }
  private def parseMultiplication: Parser[(CalcExpression, CalcExpression) => BinaryOperation] = Mul ^^^ { (left, right) => BinaryOperation(left, Mul, right) }
  private def parseDivision: Parser[(CalcExpression, CalcExpression) => BinaryOperation] = Div ^^^ { (left, right) => BinaryOperation(left, Div, right) }
  private def parseMod: Parser[(CalcExpression, CalcExpression) => BinaryOperation] = Mod ^^^ { (left, right) => BinaryOperation(left, Mod, right) }

  private def parseBinary: Parser[CalcExpression] =
    chainl1(
      chainl1(parseGrouping | parseUnary | parseLiteral | parseFunctionCall, parseMultiplication | parseDivision | parseMod),
      parseAddition | parseSubtraction
    )

  private def parseExpression: Parser[CalcExpression] = parseBinary | parseUnary | parseFunctionCall | parseLiteral | parseGrouping

  def apply(tokens: Seq[CalcToken]): Option[CalcExpression] = {
    val reader = new CalcTokenReader(tokens)
    parseExpression.apply(reader) match {
      case NoSuccess(_, _) => Option.empty
      case Success(result, _) => Option(result)
    }
  }
}
