package ru.pht.vtl.compile.ast

import ru.pht.vtl.compile.api.annotation.Mixin

class MixinConstructorStmt(
    val mode: Mixin.Constructor.Mode,
    val argumentTypes: List<String>,
    val body: List<Statement>
) : StatementMixin()