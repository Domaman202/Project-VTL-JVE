package ru.pht.vtl.ru.pht.vtl.compile.node

class ClassStmt(
    val name: String,
    val parents: List<String>,
    val open: Boolean,
    val context: String,
    val body: List<Statement>
) : Statement()