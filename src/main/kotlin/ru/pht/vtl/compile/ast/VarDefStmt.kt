package ru.pht.vtl.ru.pht.vtl.compile.node

class VarDefStmt(
    val name: String,
    val type: String,
    val mutable: Boolean,
    val value: Expression
) : Statement()