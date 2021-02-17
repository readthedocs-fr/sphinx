package net.starype.quiz.api.calc

import calc._
import org.scalatest.FunSuite

class CalcLexerTest extends FunSuite {

    val lexer: CalcLexer = new CalcLexer()

    test("lexer_should_parse_numbers") {
      assert(lexer.apply("10") === List(IntToken(10)))
      assert(lexer.apply("10.0") === List(FloatToken(10.0f)))
    }

  test("lexer_should_parse_operators") {
    assert(lexer.apply("+") === List(Plus))
    assert(lexer.apply("-") === List(Minus))
    assert(lexer.apply("/") === List(Div))
    assert(lexer.apply("*") === List(Mul))
    assert(lexer.apply("mod") === List(Mod))
  }

  test("lexer_should_parse_keyword") {
    assert(lexer.apply("(") === List(LeftParen))
    assert(lexer.apply(")") === List(RightParen))
    assert(lexer.apply(",") === List(Comma))
  }

  test("lexer_should_parse_function_identifier") {
    assert(lexer.apply("log(5.0)") === List(FunctionId("log"), LeftParen, FloatToken(5.0f), RightParen))
  }

}
