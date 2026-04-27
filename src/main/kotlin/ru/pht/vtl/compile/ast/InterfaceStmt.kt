package ru.pht.vtl.compile.ast

class InterfaceStmt(
    val name: String,
    val parents: List<String>,
    val context: String,
    val body: List<StatementClass>
) : Statement()