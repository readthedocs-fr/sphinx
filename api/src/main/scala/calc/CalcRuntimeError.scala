package calc

case class CalcRuntimeError(msg: String) extends Error(msg) { }
