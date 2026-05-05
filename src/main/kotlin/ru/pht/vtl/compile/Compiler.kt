package ru.pht.vtl.compile

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import ru.pht.vtl.compile.ast.Node
import ru.pht.vtl.compile.exception.VTLCompileException

class Compiler {
    fun compile(bytes: ByteArray): Node {
        val node = ClassNode()
        ClassReader(bytes).accept(node, 0)
        val annotations = node.invisibleAnnotations.filter { it.desc.startsWith("L$ANNOTATIONS_PACKAGE") }
        when (annotations.size) {
            0 -> throw InvalidAnnotationException("Класс \"${node.name}\" не содержит аннотаций описания представления в VTL.")
            1 -> {
                TODO()
            }
            else -> throw InvalidAnnotationException("Класс \"${node.name}\" содержит более одной аннотации описания представления в VTL.")
        }
    }

    companion object {
        const val ANNOTATIONS_PACKAGE = "ru/pht/vtl/compile/api/annotation"
        const val ANN_CLASS_DESC = "Lru/pht/vtl/compile/api/annotation/Class;"
        const val ANN_CONSTRUCTOR_DESC = "Lru/pht/vtl/compile/api/annotation/Constructor;"
        const val ANN_CONTEXT_DESC = "Lru/pht/vtl/compile/api/annotation/Context;"
        const val ANN_INTERFACE_DESC = "Lru/pht/vtl/compile/api/annotation/Interface;"
        const val ANN_METHOD_DESC = "Lru/pht/vtl/compile/api/annotation/Method;"
        const val ANN_MIXIN_DESC = "Lru/pht/vtl/compile/api/annotation/Mixin;"
        const val ANN_REMAP_DESC = "Lru/pht/vtl/compile/api/annotation/Remap;"
        const val ANN_VIRTUAL_CLASS_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualClass;"
        const val ANN_VIRTUAL_CONTEXT_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualContext;"
        const val ANN_VIRTUAL_INTERFACE_DESC = "Lru/pht/vtl/compile/api/annotation/VirtualInterface;"
    }

    class InvalidAnnotationException(message: String?) : VTLCompileException(message)
}