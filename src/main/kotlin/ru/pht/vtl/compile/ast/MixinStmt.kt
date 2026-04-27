package ru.pht.vtl.compile.ast

class MixinStmt(
    val name: String,
    val target: String,
    val mixins: List<StatementMixin>
) : Statement()