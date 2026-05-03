package ru.pht.vtl.compile.ast.utils

import ru.pht.vtl.compile.ast.*
import ru.pht.vtl.compile.node.utils.INodeVisitor

class NodePrinter private constructor() : INodeVisitor {
    private val sb = StringBuilder()
    private var indent = 0

    override fun visit(node: BlockStmt) {
        this.appendInstrStart("Block")
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: CallExpr.Internal) {
        this.appendInstrStart("Call/Internal")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Arguments", node.arguments)
        this.appendInstrEnd()
    }

    override fun visit(node: CallExpr.Virtual) {
        this.appendInstrStart("Call/Virtual")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Instance", node.instance)
        this.appendInstrArg("Arguments", node.arguments)
        this.appendInstrEnd()
    }

    override fun visit(node: CallExpr.Super) {
        this.appendInstrStart("Call/Super")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Class", node.clazz)
        this.appendInstrArg("Arguments", node.arguments)
        this.appendInstrEnd()
    }

    override fun visit(node: CallExpr.Static) {
        this.appendInstrStart("Call/Static")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Class", node.clazz)
        this.appendInstrArg("Arguments", node.arguments)
        this.appendInstrEnd()
    }

    override fun visit(node: ClassStmt) {
        this.appendInstrStart("Class")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Parents", node.parents)
        this.appendInstrArg("Open", node.open)
        this.appendInstrArg("Context", node.context)
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: ContextStmt) {
        this.appendInstrStart("Context")
        this.appendInstrArg("Name", node.name)
        if (node.parent != null)
            this.appendInstrArg("Parent", node.parent)
        else this.appendInstrArg("Parent", "(None)")
        if (node.allowKinds) {
            this.appendInstrArg("AllowKinds", true)
            this.appendInstrArg("Kinds", node.kinds)
        } else this.appendInstrArg("AllowKinds", false)
        this.appendInstrArg("VisibleFor", node.visibleFor)
        this.appendInstrArg("MixinsFor", node.mixinsFor)
        this.appendInstrEnd()
    }

    override fun visit(node: FieldDefStmt) {
        this.appendInstrStart("FieldDef")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Type", node.type)
        this.appendInstrArg("Mutable", node.mutable)
        this.appendInstrEnd()
    }

    override fun visit(node: FieldGetExpr.Instance) {
        this.appendInstrStart("FieldGet/Instance")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Instance", node.instance)
        this.appendInstrEnd()
    }

    override fun visit(node: FieldGetExpr.Static) {
        this.appendInstrStart("FieldGet/Static")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Class", node.clazz)
        this.appendInstrEnd()
    }

    override fun visit(node: FieldSetStmt.Instance) {
        this.appendInstrStart("FieldSet/Instance")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Instance", node.instance)
        this.appendInstrArg("Value", node.value)
        this.appendInstrEnd()
    }

    override fun visit(node: FieldSetStmt.Static) {
        this.appendInstrStart("FieldSet/Static")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Class", node.clazz)
        this.appendInstrArg("Value", node.value)
        this.appendInstrEnd()
    }
    override fun visit(node: IfExpr) {
        this.appendInstrStart("If")
        this.appendInstrArg("Condition", node.condition)
        this.appendInstrArg("Then", node.thenNode)
        this.appendInstrArg("Else", node.elseNode)
        this.appendInstrEnd()
    }

    override fun visit(node: IfStmt) {
        this.appendInstrStart("If")
        this.appendInstrArg("Condition", node.condition)
        this.appendInstrArg("Then", node.thenNode)
        if (node.elseNode != null)
            this.appendInstrArg("Else", node.elseNode)
        else this.appendInstrArg("Else", "(None)")
        this.appendInstrEnd()
    }

    override fun visit(node: InterfaceStmt) {
        this.appendInstrStart("Interface")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Parents", node.parents)
        this.appendInstrArg("Context", node.context)
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: MathExpr) {
        this.appendInstrStart("Math")
        this.appendInstrArg("Operation", node.operation)
        this.appendInstrArg("Operands", node.operands)
        this.appendInstrEnd()
    }


    override fun visit(node: MethodStmt) {
        this.appendInstrStart("Method")
        this.appendInstrArg("Arguments", node.arguments)
        this.appendInstrArg("ReturnType", node.returnType)
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: MixinConstructorStmt) {
        this.appendInstrStart("Mixin/Constructor")
        this.appendInstrArg("ArgumentTypes", node.argumentTypes)
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: MixinFieldStmt) {
        this.appendInstrStart("Mixin/Field")
        this.appendInstrArg("Mode", node.mode)
        this.appendInstrArg("Type", node.type)
        this.appendInstrEnd()
    }

    override fun visit(node: MixinMethodStmt) {
        this.appendInstrStart("Mixin/Method")
        this.appendInstrArg("Mode", node.mode)
        this.appendInstrArg("ArgumentTypes", node.argumentTypes)
        this.appendInstrArg("ReturnType", node.returnType)
        this.appendInstrArg("Body", node.body)
        this.appendInstrEnd()
    }

    override fun visit(node: MixinParentsStmt) {
        this.appendInstrStart("Mixin/Parents")
        this.appendInstrArg("Mode", node.mode)
        this.appendInstrArg("Value", node.parents)
        this.appendInstrEnd()
    }

    override fun visit(node: MixinStmt) {
        this.appendInstrStart("Mixin")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Target", node.target)
        this.appendInstrArg("Mixins", node.mixins)
        this.appendInstrEnd()
    }

    override fun visit(node: ReturnStmt) {
        this.appendInstrStart("Return")
        if (node.value != null)
            this.appendInstrArg("Value", node.value)
        else this.appendInstrArg("Value", "(None)")
        this.appendInstrEnd()
    }

    override fun visit(node: ValueExpr) {
        this.appendInstrStart("Value")
        this.appendInstrArg("Type", node.type)
        this.appendInstrArg("Value", node.value)
        this.appendInstrEnd()
    }

    override fun visit(node: VarDefStmt) {
        this.appendInstrStart("VarDef")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Type", node.type)
        this.appendInstrArg("Mutable", node.mutable)
        this.appendInstrArg("Value", node.value)
        this.appendInstrEnd()
    }

    override fun visit(node: VarGetExpr) {
        this.appendInstrStart("VarGet")
        this.appendInstrArg("Name", node.name)
        this.appendInstrEnd()
    }

    override fun visit(node: VarSetStmt) {
        this.appendInstrStart("VarSet")
        this.appendInstrArg("Name", node.name)
        this.appendInstrArg("Value", node.value)
        this.appendInstrEnd()
    }

    override fun visit(node: WhileStmt) {
        this.appendInstrStart("While")
        this.appendInstrArg("DoWhile", node.doWhile)
        this.appendInstrArg("Condition", node.condition)
        this.appendInstrArg("Then", node.thenNode)
        this.appendInstrEnd()
    }

    private fun enter() {
        this.indent++
    }

    private fun exit() {
        this.indent--
    }

    private fun appendStringIsolated(value: String) {
        for (char in value) {
            when (char) {
                '\n' -> this.sb.append("\\n")
                '\r' -> this.sb.append("\\r")
                '\t' -> this.sb.append("\\t")
                '\b' -> this.sb.append("\\b")
                '\'' -> this.sb.append("\\'")
                '\"' -> this.sb.append("\\\"")
                '\\' -> this.sb.append("\\\\")
                else -> this.sb.append(char)
            }
        }
    }

    private fun appendIndent() {
        if (indent > 0) {
            this.sb
                .append("|\t".repeat(this.indent - 1))
                .append("|")
                .append("\t")
        }
    }

    private fun appendNewLine() {
        this.sb.append("\n")
    }

    private fun appendInstrStart(name: String) {
        this.appendIndent()
        this.sb.append("[$name")
        this.appendNewLine()
        this.enter()
    }

    private fun appendInstrArg(name: String, value: Any?) {
        this.appendIndent()
        this.sb.append("($name): ")
        this.appendStringIsolated(value.toString())
        this.appendNewLine()
    }

    private fun appendInstrArg(name: String, value: Node) {
        this.appendIndent()
        this.sb.append("($name):")
        this.enter()
        this.appendNewLine()
        this.visit(value)
        this.exit()
        this.appendIndent()
        this.sb.append("-")
        this.appendNewLine()
    }

    private fun appendInstrArg(name: String, value: Iterable<Node>) {
        this.appendIndent()
        this.sb.append("($name):")
        this.appendNewLine()
        this.enter()
        val iterator = value.iterator()
        while (iterator.hasNext())
            this.visit(iterator.next())
        this.exit()
        this.appendIndent()
        this.sb.append("-")
        this.appendNewLine()
    }

    private fun appendInstrEnd() {
        this.exit()
        this.appendIndent()
        this.sb.append("]")
        this.appendNewLine()
    }

    companion object {
        fun print(node: Node): String {
            val printer = NodePrinter()
            printer.visit(node)
            return printer.sb.substring(0, printer.sb.lastIndex)
        }
    }
}