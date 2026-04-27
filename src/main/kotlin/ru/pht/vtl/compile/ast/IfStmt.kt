package ru.pht.vtl.compile.ast

class IfStmt(
    val condition: Expression,
    val thenNode: Statement,
    val elseNode: Statement?
) : Statement()