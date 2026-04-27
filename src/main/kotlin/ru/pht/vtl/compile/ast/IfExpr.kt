package ru.pht.vtl.compile.ast

class IfExpr(
    val condition: Expression,
    val thenNode: Expression,
    val elseNode: Expression
) : Expression()