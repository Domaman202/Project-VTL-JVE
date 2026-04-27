package ru.pht.vtl.ru.vtl.api

import ru.pht.vtl.compile.api.annotation.VirtualClass
import ru.pht.vtl.compile.api.annotation.utils.RefContext
import ru.pht.vtl.compile.api.annotation.utils.Parents

/**
 * Объект VLT.
 */
@VirtualClass(
    name = "vtl/core/Object",
    parents = Parents(list = [], using = Parents.Using.LIST),
    context = RefContext("vtl/core")
)
open class VTLObject