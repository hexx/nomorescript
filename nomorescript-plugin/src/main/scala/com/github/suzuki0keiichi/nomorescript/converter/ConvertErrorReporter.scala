package com.github.suzuki0keiichi.nomorescript.converter
import scala.reflect.internal.util.Position

trait ConvertErrorReporter {
  def addError(pos: Position, message: String)
}
