package calc

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

class CalcLexer extends RegexParsers {

  override def skipWhitespace = true

  override val whiteSpace: Regex = "[^\\S\r\n]+".r

  private def parseIntegerToken: Parser[IntToken] = "[0-9]+".r ^^ { str => IntToken(str.toInt) }

  private def parseFloatToken: Parser[FloatToken] = "([+-]?[0-9]*\\.[0-9]*)".r ^^ { str => FloatToken(str.toFloat)}

  private def parseNumber: Parser[NumberToken] = this.parseFloatToken | this.parseIntegerToken

  private def parseOperator: Parser[CalcOperator] = {
    "+" ^^^ Plus |
    "-" ^^^ Minus |
    "*" ^^^ Mul |
    "/" ^^^ Div |
    "mod" ^^^ Mod
  }

  private def parseKeyword: Parser[CalcKeyword] = {
    "(" ^^^ LeftParen |
    ")" ^^^ RightParen |
    "." ^^^ Dot |
    "," ^^^ Comma
  }

  private def parseFunctionIdentifier: Parser[CalcFunctionId] = "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => FunctionId(str) }


  private def parseTokens: Parser[List[CalcToken]] = {
    rep1(this.parseNumber | this.parseOperator | this.parseOperator | this.parseKeyword | this.parseFunctionIdentifier)
  }

  def apply(input: String): Option[List[CalcToken]] = {
    parse(this.parseTokens, input) match {
      case NoSuccess(_, _) => Option.empty
      case Success(result, _) => Option(result)
    }
  }

}