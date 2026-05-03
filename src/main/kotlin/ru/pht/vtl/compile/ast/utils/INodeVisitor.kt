package ru.pht.vtl.compile.node.utils

import ru.pht.vtl.compile.ast.*

interface INodeVisitor {
    fun visit(node: Node) {
        when (node) {
            is Expression       -> visit(node)
            is Statement        -> visit(node)
            is StatementClass   -> visit(node)
            is StatementMixin   -> visit(node)
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

    fun visit(node: Statement) {
        when (node) {
            is BlockStmt        -> visit(node)
            is ClassStmt        -> visit(node)
            is ContextStmt      -> visit(node)
            is FieldSetStmt     -> visit(node)
            is IfStmt           -> visit(node)
            is InterfaceStmt    -> visit(node)
            is MixinStmt        -> visit(node)
            is ReturnStmt       -> visit(node)
            is VarSetStmt       -> visit(node)
            is VarDefStmt       -> visit(node)
            is WhileStmt        -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: StatementClass) {
        when (node) {
            is FieldDefStmt -> visit(node)
            is MethodStmt   -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: StatementMixin) {
        when (node) {
            is MixinParentsStmt     -> visit(node)
            is MixinFieldStmt       -> visit(node)
            is MixinConstructorStmt -> visit(node)
            is MixinMethodStmt      -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: CallExpr) {
        when (node) {
            is CallExpr.Internal    -> visit(node)
            is CallExpr.Virtual     -> visit(node)
            is CallExpr.Super       -> visit(node)
            is CallExpr.Static      -> visit(node)
            is CallExpr.Dynamic     -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: FieldGetExpr) {
        when (node) {
            is FieldGetExpr.Instance    -> visit(node)
            is FieldGetExpr.Static      -> visit(node)
            is FieldGetExpr.Dynamic     -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: FieldSetStmt) {
        when (node) {
            is FieldSetStmt.Instance    -> visit(node)
            is FieldSetStmt.Static      -> visit(node)
            is FieldSetStmt.Dynamic     -> visit(node)
            else -> throw IllegalArgumentException("Неизвестный тип узла \"${node.javaClass}\"")
        }
    }

    fun visit(node: BlockStmt)
    fun visit(node: ClassStmt)
    fun visit(node: ContextStmt)
    fun visit(node: CallExpr.Internal)
    fun visit(node: CallExpr.Virtual)
    fun visit(node: CallExpr.Super)
    fun visit(node: CallExpr.Static)
    fun visit(node: CallExpr.Dynamic)
    fun visit(node: FieldDefStmt)
    fun visit(node: FieldGetExpr.Instance)
    fun visit(node: FieldGetExpr.Static)
    fun visit(node: FieldGetExpr.Dynamic)
    fun visit(node: FieldSetStmt.Instance)
    fun visit(node: FieldSetStmt.Static)
    fun visit(node: FieldSetStmt.Dynamic)
    fun visit(node: IfExpr)
    fun visit(node: IfStmt)
    fun visit(node: InterfaceStmt)
    fun visit(node: MathExpr)
    fun visit(node: MethodStmt)
    fun visit(node: MixinConstructorStmt)
    fun visit(node: MixinFieldStmt)
    fun visit(node: MixinMethodStmt)
    fun visit(node: MixinParentsStmt)
    fun visit(node: MixinStmt)
    fun visit(node: ReturnStmt)
    fun visit(node: ValueExpr)
    fun visit(node: VarDefStmt)
    fun visit(node: VarGetExpr)
    fun visit(node: VarSetStmt)
    fun visit(node: WhileStmt)
}