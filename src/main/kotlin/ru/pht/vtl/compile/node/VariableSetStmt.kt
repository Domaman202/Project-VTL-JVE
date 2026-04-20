package ru.pht.vtl.ru.pht.vtl.compile.node

class VariableSetStmt(
    val name: String,
    val value: Expression
) : Statement()