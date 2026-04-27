package ru.pht.vtl.compile.ast

class MethodStmt(
    val name: String,
    // (Имя, Тип)
    val arguments: List<Pair<String, String>>,
    val returnType: String,
    val body: List<Statement>
) : StatementClass()