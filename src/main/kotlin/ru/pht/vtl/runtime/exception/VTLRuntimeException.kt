package ru.pht.vtl.runtime.exception

/**
 * Ошибка runtime составляющей VLT.
 */
open class VTLRuntimeException : RuntimeException {
    constructor(): super()
    constructor(message: String?): super(message)
    constructor(message: String?, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)
    protected constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}