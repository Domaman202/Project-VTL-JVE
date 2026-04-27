package ru.pht.vtl.compile.ast

class ContextStmt(
    val name: String,
    val parent: String?,
    val allowKinds: Boolean,
    val kinds: List<String>,
    val visibleFor: List<String>,
    val mixinsFor: List<String>
) : Statement()