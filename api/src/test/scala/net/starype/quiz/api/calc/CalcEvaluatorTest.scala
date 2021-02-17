package net.starype.quiz.api.calc

import calc._
import org.scalatest.FunSuite

class CalcEvaluatorTest extends FunSuite {

  val evaluator = new CalcEvaluator()

  test("evaluator_should_evaluate_literals") {
    assert(evaluator.evalCalcExpression(IntLit(5)) === 5)
    assert(evaluator.evalCalcExpression(FloatLit(5)) === 5.0f)
  }

  test("evaluator_should_parse_function_call") {
    assert(evaluator.evalCalcExpression(FunctionCall(ASTFunctionId("log"), List(IntLit(1)))) === 0)
  }

  test("evaluator_should_evaluate_unary_expressions") {
    assert(evaluator.evalCalcExpression(UnaryOperation(Minus, IntLit(-5))) === 5)
    assert(evaluator.evalCalcExpression(UnaryOperation(Minus, FloatLit(-5.0f))) === 5.0f)
  }

  test("evaluator_should_evaluate_binary_expressions") {
    assert(evaluator.evalCalcExpression(BinaryOperation(IntLit(5), Plus, FloatLit(5.0f))) === 10.0f)
    assert(evaluator.evalCalcExpression(BinaryOperation(IntLit(5), Minus, IntLit(5))) === 0)
    assert(evaluator.evalCalcExpression(BinaryOperation(IntLit(5), Div, IntLit(5))) === 1)
    assert(evaluator.evalCalcExpression(BinaryOperation(IntLit(5), Mul, IntLit(5))) === 25)
    assert(evaluator.evalCalcExpression(BinaryOperation(IntLit(9), Mod, IntLit(4))) === 1)
  }

}
