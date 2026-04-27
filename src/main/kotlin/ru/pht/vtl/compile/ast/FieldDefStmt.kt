package ru.pht.vtl.compile.ast

open class FieldDefStmt(
    val name: String,
    val type: String,
    val mutable: Boolean
) : StatementClass()