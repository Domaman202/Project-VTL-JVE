package ru.pht.vtl.runtime.api

import ru.pht.vtl.runtime.exception.VTLSecurityException

object SecureContextPool {
    private val POOL = ArrayList<SecureContext>()

    fun push(context: SecureContext) {
        POOL.add(context)
    }

    fun findOrNull(name: String): SecureContext? {
        return POOL.find { it.name == name }
    }

    fun findOrThrow(name: String): SecureContext {
        return this.findOrNull(name)
            ?: throw ContextNotFoundException("Не удалось найти контекст безопасности \"${name}\"")
    }

    /**
     * Ошибка поиска контекста.
     */
    class ContextNotFoundException(message: String?) : VTLSecurityException(message)
}