package ru.pht.vtl.compile

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode
import ru.pht.vtl.compile.ast.ClassStmt
import ru.pht.vtl.compile.ast.ContextStmt
import ru.pht.vtl.compile.ast.InterfaceStmt
import ru.pht.vtl.compile.ast.MixinStmt
import ru.pht.vtl.compile.ast.Node
import ru.pht.vtl.compile.exception.VTLCompileException

/**
 * Компилятор Java классов в VTL.
 */
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
        TODO()
    }

    private fun compileInterface(cnode: ClassNode, anode: AnnotationNode): InterfaceStmt {
        TODO()
    }

    private fun compileContext(cnode: ClassNode, anode: AnnotationNode): ContextStmt {
        TODO()
    }

    private fun compileMixin(cnode: ClassNode, anode: AnnotationNode): MixinStmt {
        TODO()
    }

    private fun compileVirtualClass(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
    }

    private fun compileVirtualInterface(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
    }

    private fun compileRemap(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
    }

    private fun compileVirtualContext(cnode: ClassNode, anode: AnnotationNode) {
        TODO()
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
    }

    class InvalidAnnotationException(message: String?) : VTLCompileException(message)
}