package ru.pht.vtl.ru.pht.vtl.compile.node

class WhileStmt(
    val doWhile: Boolean,
    val condition: Expression,
    val thenNode: Statement,
) : Statement()