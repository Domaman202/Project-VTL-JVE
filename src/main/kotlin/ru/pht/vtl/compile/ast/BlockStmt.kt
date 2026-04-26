package ru.pht.vtl.ru.pht.vtl.compile.ast

import ru.pht.vtl.ru.pht.vtl.compile.node.Statement

class BlockStmt(
    val body: List<Statement>
) : Statement()