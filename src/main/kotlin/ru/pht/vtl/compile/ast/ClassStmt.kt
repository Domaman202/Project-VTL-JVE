package ru.pht.vtl.compile.ast

class ClassStmt(
    val name: String,
    val parents: List<String>,
    val open: Boolean,
    val context: String,
    val body: List<StatementClass>
) : Statement()