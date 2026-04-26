package ru.pht.vtl.ru.pht.vtl.compile.node

class ValueExpr(
    val type: Type,
    val value: Any?
) : Expression() {
    enum class Type {
        NULL,
        BOOL,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING
    }
}