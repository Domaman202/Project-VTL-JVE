package ru.pht.vtl.ru.pht.vtl.compile.node

class InterfaceStmt(
    val name: String,
    val parents: List<String>,
    val open: Boolean,
    val context: String,
    val body: List<Statement>
) : Statement()