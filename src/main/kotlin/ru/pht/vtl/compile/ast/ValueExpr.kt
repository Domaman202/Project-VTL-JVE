package ru.pht.vtl.compile.ast

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
        STRING,
        TYPE
    }
}