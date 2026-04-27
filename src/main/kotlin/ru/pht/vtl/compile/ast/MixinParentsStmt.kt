package ru.pht.vtl.compile.ast

import ru.pht.vtl.compile.api.annotation.Mixin

class MixinParentsStmt(
    val mode: Mixin.Parents.Mode,
    val parents: List<String>
) : StatementMixin()