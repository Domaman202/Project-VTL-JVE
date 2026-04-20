package ru.pht.vtl.ru.pht.vtl.compile.node

import ru.pht.vtl.ru.pht.vtl.compile.api.annotation.Mixin

class MixinStmt(
    val name: String,
    val target: String,
    val mixins: List<StmtMixinElement>
) : Statement() {
    open class StmtMixinElement : Statement()

    class MixinParentsStmt(
        val mode: Mixin.Parents.Mode,
        val parents: List<String>
    ) : StmtMixinElement()

    class MixinFieldStmt(
        val mode: Mixin.Field.Mode,
        val name: String,
        val type: String,
    ) : StmtMixinElement()

    class MixinMethodStmt(
        val mode: Mixin.Method.Mode,
        val name: String,
        val argumentTypes: List<String>,
        val returnType: String,
    ) : StmtMixinElement()
}