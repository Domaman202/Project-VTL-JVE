package ru.pht.vtl.ru.pht.vtl.compile

import ru.pht.vtl.ru.pht.vtl.compile.node.CallExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.ClassStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.Expression
import ru.pht.vtl.ru.pht.vtl.compile.node.FieldGetExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.FieldSetStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.IfExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.IfStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.InterfaceStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MathExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.MethodStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.Node
import ru.pht.vtl.ru.pht.vtl.compile.node.ReturnStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.Statement
import ru.pht.vtl.ru.pht.vtl.compile.node.ValueExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.VariableGetExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.VariableSetStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.VariableStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.WhileStmt

object NodePrinter {
    fun print(node: Node): String {
        val sb = StringBuilder()
        visit(node, sb, 0)
        return sb.toString()
    }

    private fun visit(node: Node, sb: StringBuilder, indent: Int) {
        when (node) {
            is Expression -> visit(node, sb, indent)
            is Statement -> visit(node, sb, indent)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    private fun visit(node: Expression, sb: StringBuilder, indent: Int) {
        when (node) {
            is CallExpr         -> visit(node, sb, indent)
            is FieldGetExpr     -> visit(node, sb, indent)
            is IfExpr           -> visit(node, sb, indent)
            is MathExpr         -> visit(node, sb, indent)
            is ValueExpr        -> visit(node, sb, indent)
            is VariableGetExpr  -> visit(node, sb, indent)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    private fun visit(node: Statement, sb: StringBuilder, indent: Int) {
        when (node) {
            is ClassStmt        -> visit(node, sb, indent)
            is FieldSetStmt     -> visit(node, sb, indent)
            is IfStmt           -> visit(node, sb, indent)
            is InterfaceStmt    -> visit(node, sb, indent)
            is MethodStmt       -> visit(node, sb, indent)
            is MixinStmt        -> visit(node, sb, indent)
            is ReturnStmt       -> visit(node, sb, indent)
            is VariableSetStmt  -> visit(node, sb, indent)
            is VariableStmt     -> visit(node, sb, indent)
            is WhileStmt        -> visit(node, sb, indent)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    private fun visit(node: CallExpr, sb: StringBuilder, indent: Int) {
        TODO("CallExpr")
    }

    private fun visit(node: FieldGetExpr, sb: StringBuilder, indent: Int) {
        TODO("FieldGetExpr")
    }

    private fun visit(node: IfExpr, sb: StringBuilder, indent: Int) {
        TODO("IfExpr")
    }

    private fun visit(node: MathExpr, sb: StringBuilder, indent: Int) {
        TODO("MathExpr")
    }

    private fun visit(node: ValueExpr, sb: StringBuilder, indent: Int) {
        TODO("ValueExpr")
    }

    private fun visit(node: VariableGetExpr, sb: StringBuilder, indent: Int) {
        TODO("VariableGetExpr")
    }

    private fun visit(node: ClassStmt, sb: StringBuilder, indent: Int) {
        TODO("ClassStmt")
    }

    private fun visit(node: FieldSetStmt, sb: StringBuilder, indent: Int) {
        TODO("FieldSetStmt")
    }

    private fun visit(node: IfStmt, sb: StringBuilder, indent: Int) {
        TODO("IfStmt")
    }

    private fun visit(node: InterfaceStmt, sb: StringBuilder, indent: Int) {
        TODO("InterfaceStmt")
    }

    private fun visit(node: MethodStmt, sb: StringBuilder, indent: Int) {
        TODO("MethodStmt")
    }

    private fun visit(node: MixinStmt, sb: StringBuilder, indent: Int) {
        TODO("MixinStmt")
    }

    private fun visit(node: ReturnStmt, sb: StringBuilder, indent: Int) {
        TODO("ReturnStmt")
    }

    private fun visit(node: VariableSetStmt, sb: StringBuilder, indent: Int) {
        TODO("VariableSetStmt")
    }

    private fun visit(node: VariableStmt, sb: StringBuilder, indent: Int) {
        TODO("VariableStmt")
    }

    private fun visit(node: WhileStmt, sb: StringBuilder, indent: Int) {
        TODO("WhileStmt")
    }
}