package ru.pht.vtl.ru.pht.vtl.compile.node

open class FieldGetExpr(
    val name: String
) : Expression() {
    class Instance(
        name: String,
        val instance: Expression
    ) : FieldGetExpr(name)

    class Static(
        name: String,
        val clazz: String
    ) : FieldGetExpr(name)
}