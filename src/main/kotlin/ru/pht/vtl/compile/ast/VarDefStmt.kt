package ru.pht.vtl.compile.ast

class VarDefStmt(
    val name: String,
    val type: String,
    val mutable: Boolean,
    val value: Expression
) : Statement()