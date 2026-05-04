package ru.pht.vtl.compile.ast

class BlockExpr(
    val firstStmt: List<Statement>,
    val lastExpr: Expression
) : Expression()