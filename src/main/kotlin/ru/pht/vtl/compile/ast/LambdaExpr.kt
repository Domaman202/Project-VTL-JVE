package ru.pht.vtl.compile.ast

abstract class LambdaExpr(
    val body: List<Statement>
) : Expression() {
    class Untyped(
        // (Имя, Тип)
        val arguments: List<Pair<String, String>>,
        val returnType: String,
        body: List<Statement>
    ) : LambdaExpr(body)

    class Typed(
        val type: String,
        // (Имя)
        val arguments: List<String>,
        body: List<Statement>
    ) : LambdaExpr(body)
}