package ru.pht.vtl.compile.ast

open class FieldSetStmt(
    val name: String,
    val value: Expression
) : Statement() {
    class Instance(
        name: String,
        value: Expression,
        val instance: Expression
    ) : FieldSetStmt(name, value)

    class Static(
        name: String,
        value: Expression,
        val clazz: String
    ) : FieldSetStmt(name, value)
}