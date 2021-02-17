package net.starype.quiz.api.calc

import calc._
import org.scalatest.FunSuite

class CalcParserTest extends FunSuite {

  val parser = new CalcParser()

  test("parser_should_parse_literals") {
    assert(parser.apply(Seq(IntToken(5))) === IntLit(5))
    assert(parser.apply(Seq(FloatToken(5))) === FloatLit(5.0f))
  }

  test("parser_should_parse_function_call") {
    assert(parser.apply(Seq(FunctionId("log"), OpeningBracket, FloatToken(5.0f), ClosingBracket)) === FunctionCall(ASTFunctionId("log"), List(FloatLit(5.0f))))
  }

  test("parser_should_parse_unary_expression") {
    assert(parser.apply(Seq(Minus, IntToken(5))) === UnaryOperation(Minus, IntLit(5)))
    assert(parser.apply(Seq(Minus, FloatToken(5.0f))) === UnaryOperation(Minus, FloatLit(5.0f)))
  }

  test("parser_should_parse_binary_expression") {
    assert(parser.apply(Seq(IntToken(5), Minus, IntToken(5))) === BinaryOperation(IntLit(5), Minus, IntLit(5)))
    assert(parser.apply(Seq(FloatToken(5.0f), Plus, IntToken(5))) === BinaryOperation(FloatLit(5.0f), Plus, IntLit(5)))
    assert(parser.apply(Seq(IntToken(5), Mul, IntToken(5))) === BinaryOperation(IntLit(5), Mul, IntLit(5)))
    assert(parser.apply(Seq(IntToken(5), Div, IntToken(5))) === BinaryOperation(IntLit(5), Div, IntLit(5)))
    assert(parser.apply(Seq(IntToken(5), Mod, IntToken(5))) === BinaryOperation(IntLit(5), Mod, IntLit(5)))
  }

  test("parser_should_parse_complex_expressions") {
    assert(parser.apply(Seq(FunctionId("log"), OpeningBracket, FloatToken(5.0f), ClosingBracket, Plus, IntToken(5), Mul, FloatToken(5.0f))) === BinaryOperation(FunctionCall(ASTFunctionId("log"), List(FloatLit(5.0f))), Plus, BinaryOperation(IntLit(5), Mul, FloatLit(5.0f))))
  }

}
