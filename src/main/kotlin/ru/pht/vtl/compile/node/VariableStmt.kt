package ru.pht.vtl.ru.pht.vtl.compile.node

class VariableStmt(
    val name: String,
    val type: String,
    val mutable: Boolean,
    val value: Expression
) : Statement()