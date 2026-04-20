package ru.pht.vtl.ru.pht.vtl.compile.node

class IfStmt(
    val condition: Expression,
    val thenNode: Statement,
    val elseNode: Statement?
) : Statement()