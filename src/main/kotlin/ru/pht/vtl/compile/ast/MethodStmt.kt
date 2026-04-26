package ru.pht.vtl.ru.pht.vtl.compile.node

class MethodStmt(
    val name: String,
    // (Имя, Тип)
    val arguments: List<Pair<String, String>>,
    val returnType: String,
    val body: List<Statement>
) : Statement()