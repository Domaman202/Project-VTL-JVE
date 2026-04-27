package ru.pht.vtl.compile.ast

import ru.pht.vtl.compile.api.annotation.Mixin

class MixinFieldStmt(
    val mode: Mixin.Field.Mode,
    val name: String,
    val type: String
) : StatementMixin()