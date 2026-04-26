package ru.pht.vtl.ru.pht.vtl.compile.node

class VarSetStmt(
    val name: String,
    val value: Expression
) : Statement()