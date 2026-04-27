package ru.pht.vtl.compile.ast

class VarSetStmt(
    val name: String,
    val value: Expression
) : Statement()