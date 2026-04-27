package ru.pht.vtl.compile.ast

open class CallExpr(
    val name: String,
    val arguments: List<Expression>
) : Expression() {
    class Internal(
        name: String,
        arguments: List<Expression>,
    ) : CallExpr(name, arguments)

    class Virtual(
        name: String,
        arguments: List<Expression>,
        val instance: Expression
    ) : CallExpr(name, arguments)

    class Static(
        name: String,
        arguments: List<Expression>,
        val clazz: String
    ) : CallExpr(name, arguments)
}