package ru.pht.vtl.compile.ast

class MathExpr(
    val operation: Operation,
    val operands: List<Expression>
) : Expression() {
    enum class Operation {
        // Базовая математика
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        // Продвинутая математика
        NEG,
        POW,
        SQRT,
        // Операции сравнения
        GREAT,
        GREAT_EQ,
        LESS,
        LESS_EQ,
        // Битовые операции
        BIT_AND,
        BIT_OR,
        BIT_XOR,
        // Битовые операции сдвига
        BIT_SHIFT_LEFT,
        BIT_SHIFT_RIGHT_LOGIC,
        BIT_SHIFT_RIGHT_ARITHMETIC,
        // Логические операции
        AND,
        OR,
        XOR,
        NOT
    }
}