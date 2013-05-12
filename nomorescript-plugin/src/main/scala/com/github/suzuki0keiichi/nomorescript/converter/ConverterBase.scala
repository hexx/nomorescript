package com.github.suzuki0keiichi.nomorescript.converter

import com.github.suzuki0keiichi.nomorescript.annotation.global
import scala.tools.nsc.SubComponent
import com.github.suzuki0keiichi.nomorescript.trees.NoMoreScriptTree

trait ConverterBase {
  self: SubComponent =>

  import global._

  def toTree(tree: Tree, scopedVars: ScopedVariables, returnValue: Boolean, memberNames: Map[String, String] = Map.empty[String, String]): NoMoreScriptTree

  def findClass(name: String): Option[ClassDef]

  class ScopedVariables(private val parent: ScopedVariables) {
    private val vars = scala.collection.mutable.Map[String, Symbol]()

    private def exists(name: String): Boolean = {
      if (vars.contains(name)) {
        true
      } else {
        false
      }
    }

    private def renamePut(origName: String, sym: Symbol, count: Int): String = {
      val newName = origName + "__scoped__" + count

      if (exists(newName)) {
        renamePut(origName, sym, count + 1)
      } else {
        put(newName, sym)
      }
    }

    def put(name: String, sym: Symbol): String = {
      if (exists(name)) {
        renamePut(name, sym, 1)
      } else {
        vars.put(name, sym)
        name
      }
    }

    def getName(sym: Symbol): String = {
      val currentResult = vars.collectFirst {
        case (name, varsSym) if (varsSym == sym) => name
      }

      currentResult match {
        case Some(name) => name
        case None => if (parent != null) { parent.getName(sym) } else { sym.name.toString() }
      }
    }
  }
}
