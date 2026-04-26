package ru.pht.vtl.ru.pht.vtl.compile.node.utils

import ru.pht.vtl.ru.pht.vtl.compile.ast.BlockStmt
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
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt.MixinConstructorStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt.MixinFieldStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt.MixinMethodStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt.MixinParentsStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.MixinStmt.StmtMixinElement
import ru.pht.vtl.ru.pht.vtl.compile.node.Node
import ru.pht.vtl.ru.pht.vtl.compile.node.ReturnStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.Statement
import ru.pht.vtl.ru.pht.vtl.compile.node.ValueExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.VarGetExpr
import ru.pht.vtl.ru.pht.vtl.compile.node.VarSetStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.VarDefStmt
import ru.pht.vtl.ru.pht.vtl.compile.node.WhileStmt

interface INodeVisitor {
    fun visit(node: Node) {
        when (node) {
            is Expression   -> visit(node)
            is Statement    -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: Expression) {
        when (node) {
            is CallExpr         -> visit(node)
            is FieldGetExpr     -> visit(node)
            is IfExpr           -> visit(node)
            is MathExpr         -> visit(node)
            is ValueExpr        -> visit(node)
            is VarGetExpr       -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: CallExpr) {
        when (node) {
            is CallExpr.Internal    -> visit(node)
            is CallExpr.Virtual     -> visit(node)
            is CallExpr.Static      -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: FieldGetExpr) {
        when (node) {
            is FieldGetExpr.Instance    -> visit(node)
            is FieldGetExpr.Static      -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: Statement) {
        when (node) {
            is BlockStmt        -> visit(node)
            is ClassStmt        -> visit(node)
            is FieldSetStmt     -> visit(node)
            is IfStmt           -> visit(node)
            is InterfaceStmt    -> visit(node)
            is MethodStmt       -> visit(node)
            is MixinStmt        -> visit(node)
            is StmtMixinElement -> visit(node)
            is ReturnStmt       -> visit(node)
            is VarSetStmt       -> visit(node)
            is VarDefStmt       -> visit(node)
            is WhileStmt        -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: FieldSetStmt) {
        when (node) {
            is FieldSetStmt.Instance    -> visit(node)
            is FieldSetStmt.Static      -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: StmtMixinElement) {
        when (node) {
            is MixinParentsStmt     -> visit(node)
            is MixinFieldStmt       -> visit(node)
            is MixinConstructorStmt -> visit(node)
            is MixinMethodStmt      -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: CallExpr.Internal)
    fun visit(node: CallExpr.Virtual)
    fun visit(node: CallExpr.Static)
    fun visit(node: FieldGetExpr.Instance)
    fun visit(node: FieldGetExpr.Static)
    fun visit(node: IfExpr)
    fun visit(node: MathExpr)
    fun visit(node: ValueExpr)
    fun visit(node: VarGetExpr)
    fun visit(node: BlockStmt)
    fun visit(node: ClassStmt)
    fun visit(node: FieldSetStmt.Instance)
    fun visit(node: FieldSetStmt.Static)
    fun visit(node: IfStmt)
    fun visit(node: InterfaceStmt)
    fun visit(node: MethodStmt)
    fun visit(node: MixinStmt)
    fun visit(node: MixinParentsStmt)
    fun visit(node: MixinFieldStmt)
    fun visit(node: MixinConstructorStmt)
    fun visit(node: MixinMethodStmt)
    fun visit(node: ReturnStmt)
    fun visit(node: VarSetStmt)
    fun visit(node: VarDefStmt)
    fun visit(node: WhileStmt)
}