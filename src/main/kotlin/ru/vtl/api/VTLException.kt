package ru.pht.vtl.ru.vtl.api

import ru.pht.vtl.compile.api.annotation.VirtualClass
import ru.pht.vtl.compile.api.annotation.utils.RefContext
import ru.pht.vtl.ru.pht.vtl.compile.api.annotation.Constructor

/**
 * Исключение VLT.
 */
@VirtualClass(
    name = "vtl/core/Exception",
    context = RefContext("vtl/core")
)
open class VTLException : Exception {
    @Constructor
    constructor(): super()
    @Constructor
    constructor(message: String?): super(message)
    @Constructor
    constructor(message: String?, cause: Throwable?): super(message, cause)
    @Constructor
    constructor(cause: Throwable?): super(cause)
}