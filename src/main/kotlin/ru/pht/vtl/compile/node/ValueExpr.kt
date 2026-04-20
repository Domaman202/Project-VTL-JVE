package ru.pht.vtl.ru.pht.vtl.compile.node

class ValueExpr(
    val type: Type,
    val value: String
) : Expression() {
    enum class Type {
        BOOL,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING
    }
}