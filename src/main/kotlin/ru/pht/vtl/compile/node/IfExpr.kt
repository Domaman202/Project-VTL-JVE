package ru.pht.vtl.ru.pht.vtl.compile.node

class IfExpr(
    val condition: Expression,
    val thenNode: Expression,
    val elseNode: Expression
) : Expression()