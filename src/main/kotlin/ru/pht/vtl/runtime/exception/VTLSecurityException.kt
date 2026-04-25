package ru.pht.vtl.ru.pht.vtl.runtime.exception

/**
 * Ошибка безопасности runtime составляющей VTL.
 */
open class VTLSecurityException : VTLRuntimeException {
    constructor(): super()
    constructor(message: String?): super(message)
    constructor(message: String?, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)
    protected constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}