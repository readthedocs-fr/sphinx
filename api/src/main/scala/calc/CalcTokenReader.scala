package calc

import scala.util.parsing.input.{NoPosition, Position, Reader}

class CalcTokenReader(calcTokens: Seq[CalcToken]) extends Reader[CalcToken] {

  override def first: CalcToken = calcTokens.head

  override def rest: Reader[CalcToken] = new CalcTokenReader(calcTokens.tail)

  override def pos: Position = NoPosition

  override def atEnd: Boolean = calcTokens.isEmpty
}
