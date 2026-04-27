package ru.pht.vtl.compile.ast

class WhileStmt(
    val doWhile: Boolean,
    val condition: Expression,
    val thenNode: Statement,
) : Statement()