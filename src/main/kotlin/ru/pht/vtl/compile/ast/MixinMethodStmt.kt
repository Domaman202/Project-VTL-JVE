package ru.pht.vtl.compile.ast

import ru.pht.vtl.compile.api.annotation.Mixin

class MixinMethodStmt(
    val mode: Mixin.Method.Mode,
    val name: String,
    val argumentTypes: List<String>,
    val returnType: String,
    val body: List<Statement>
) : StatementMixin()