package ru.pht.vtl.compile.ast

import org.objectweb.asm.tree.MethodNode

class FunInterfaceStmt(
    name: String,
    parents: List<String>,
    context: String,
    val invoke: MethodNode,
    body: List<StatementClass>
) : InterfaceStmt(
    name,
    parents,
    context,
    body
)