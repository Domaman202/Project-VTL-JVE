package ru.pht.vtl.compile

import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode
import ru.pht.vtl.compile.api.annotation.utils.OpenModifier
import ru.pht.vtl.compile.api.annotation.utils.Parents
import ru.pht.vtl.compile.ast.ClassStmt
import ru.pht.vtl.compile.ast.ContextStmt
import ru.pht.vtl.compile.ast.InterfaceStmt
import ru.pht.vtl.compile.ast.MixinStmt
import ru.pht.vtl.compile.ast.Node
import ru.pht.vtl.compile.exception.VTLCompileException

/**
 * Компилятор Java классов в VTL.
 */
@Suppress("UNCHECKED_CAST")
class Compiler {
    /**
     * Компиляция Java класса в VTL.
     *
     * @param bytes Байткод класса.
     * @return VTL AST.
     * @throws InvalidAnnotationException Ошибка анализа аннотаций описания представления VTL.
     */
    fun compile(bytes: ByteArray): Node? {
        val node = ClassNode()
        ClassReader(bytes).accept(node, 0)
        val annotations = node.invisibleAnnotations.filter { it.desc.startsWith("L$ANNOTATIONS_PACKAGE") }
        when (annotations.size) {
            0 -> throw InvalidAnnotationException("Класс \"${node.name}\" не содержит аннотаций описания представления в VTL.")
            1 -> {
                val annotation = annotations.first()
                when (annotation.desc) {
                    ANN_CLASS_DESC              -> return compileClass(node, annotation)
                    ANN_CONTEXT_DESC            -> return compileContext(node, annotation)
                    ANN_INTERFACE_DESC          -> return compileInterface(node, annotation)
                    ANN_MIXIN_DESC              -> return compileMixin(node, annotation)
                    ANN_VIRTUAL_CLASS_DESC      -> compileVirtualClass(node, annotation)
                    ANN_VIRTUAL_CONTEXT_DESC    -> compileVirtualContext(node, annotation)
                    ANN_VIRTUAL_INTERFACE_DESC  -> compileVirtualInterface(node, annotation)
                    else -> throw InvalidAnnotationException("Класс \"${node.name}\" содержит неопознанную аннотацию \"${annotation.desc}\"")
                }
                return null
            }
            else -> throw InvalidAnnotationException("Класс \"${node.name}\" содержит более одной аннотации описания представления в VTL.")
        }
    }

    private fun compileClass(cnode: ClassNode, anode: AnnotationNode): ClassStmt {
        val name = anode.valueNameOrJVM(cnode)
        val parents = anode.valueParentsOr(cnode)
        val open = anode.valueOpenOrJVM(cnode)
        val context = anode.valueContextOrNull() ?: CTX_GLOBAL
        TODO()
    }

    private fun compileInterface(cnode: ClassNode, anode: AnnotationNode): InterfaceStmt {
        val name = anode.valueNameOrJVM(cnode)
        val parents = anode.valueParentsOr(cnode)
        val context = anode.valueContextOrNull() ?: CTX_GLOBAL
        TODO()
    }

    private fun compileContext(cnode: ClassNode, anode: AnnotationNode): ContextStmt {
        TODO()
    }

    private fun compileMixin(cnode: ClassNode, anode: AnnotationNode): MixinStmt {
        TODO()
    }

    private fun compileVirtualClass(cnode: ClassNode, anode: AnnotationNode) {
        val name = anode.valueNameOrJVM(cnode)
        val parents = anode.valueParentsOr(cnode)
        val context = anode.valueContextOrNull() ?: CTX_GLOBAL
        TODO()
    }

    private fun compileVirtualInterface(cnode: ClassNode, anode: AnnotationNode) {
        val name = anode.valueNameOrJVM(cnode)
        val parents = anode.valueParentsOr(cnode)
        val context = anode.valueContextOrNull() ?: CTX_GLOBAL
        TODO()
    }

    private fun compileRemap(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
    }

    private fun compileVirtualContext(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
    }

    private fun <T> AnnotationNode.valueOrNull(name: String): T? {
        if (this.values.isNullOrEmpty())
            return null
        val index = this.values.indexOf(name)
        if (index == -1)
            return null
        return this.values[index + 1] as T?
    }

    private fun AnnotationNode.valueAsClass(): String {
        this.valueOrNull<String>("name")?.let { return it }
        this.valueOrNull<Type>("clazz")?.let { return it.internalName }
        throw IllegalStateException()
    }

    private fun AnnotationNode.valueNameOrJVM(node: ClassNode): String {
        return this.valueOrNull("name") ?: node.name
    }

    private fun AnnotationNode.valueParentsOr(node: ClassNode): List<String> {
        val parents = this.valueParentsOrNull()
        when (parents?.first) {
            Parents.Using.LIST -> return parents.second
            Parents.Using.LIST_OR_JVM -> if (parents.second.isNotEmpty()) return parents.second
            else -> { /* Empty */ }
        }
        return (listOf(node.superName) + node.interfaces)
    }

    private fun AnnotationNode.valueParentsOrNull(): Pair<Parents.Using, List<String>>? {
        val inner = this.valueOrNull<AnnotationNode>("parents") ?: return null
        val using = inner.valueOrNull<Array<String>>("using")?.get(1)?.let(Parents.Using::valueOf) ?: Parents.Using.LIST_OR_JVM
        val list = inner.valueOrNull<List<AnnotationNode>>("list")?.map { it.valueAsClass() } ?: listOf()
        return Pair(using, list)
    }

    private fun AnnotationNode.valueOpenOrJVM(node: ClassNode): OpenModifier {
        return this.valueOrNull("open") ?: if ((node.access and Opcodes.ACC_FINAL) != 0) OpenModifier.FINAL else OpenModifier.FINAL
    }

    private fun AnnotationNode.valueContextOrNull(): String? {
        val inner = this.valueOrNull<AnnotationNode>("context") ?: return null
        inner.valueOrNull<String>("name")?.let { return it }
        inner.valueOrNull<Type>("clazz")?.let { return it.internalName }
        return null
    }

    companion object {
        private const val ANNOTATIONS_PACKAGE = "ru/pht/vtl/compile/api/annotation"
        private const val ANN_CLASS_DESC = "Lru/pht/vtl/compile/api/annotation/Class;"
        private const val ANN_CONSTRUCTOR_DESC = "Lru/pht/vtl/compile/api/annotation/Constructor;"
        private const val ANN_CONTEXT_DESC = "Lru/pht/vtl/compile/api/annotation/Context;"
        private const val ANN_INTERFACE_DESC = "Lru/pht/vtl/compile/api/annotation/Interface;"
        private const val ANN_METHOD_DESC = "Lru/pht/vtl/compile/api/annotation/Method;"
        private const val ANN_MIXIN_DESC = "Lru/pht/vtl/compile/api/annotation/Mixin;"
        private const val ANN_REMAP_DESC = "Lru/pht/vtl/compile/api/annotation/Remap;"
        private const val ANN_VIRTUAL_CLASS_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualClass;"
        private const val ANN_VIRTUAL_CONTEXT_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualContext;"
        private const val ANN_VIRTUAL_INTERFACE_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualInterface;"

        const val CTX_GLOBAL = "global"

        const val TYPE_VTL_OBJECT = "vtl/core/Object"
    }

    class InvalidAnnotationException(message: String?) : VTLCompileException(message)
}