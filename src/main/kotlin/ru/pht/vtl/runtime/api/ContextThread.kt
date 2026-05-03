package ru.pht.vtl.runtime.api

import ru.pht.vtl.runtime.exception.VTLRuntimeException


/**
 * Утилиты для работы с потоками с привязкой к контекстам.
 */
object ContextThread {
    private val SECURE_CONTEXT = ScopedValue.newInstance<SecureContext>()
    private val CLASSLOADING_CONTEXT = ScopedValue.newInstance<ClassLoadingContext>()

    /**
     * Создание нового потока с привязкой к контекстам.
     *
     * @param secureContext Контекст безопасности.
     * @param classLoadingContext Контекст загрузки классов.
     * @param name Имя потока.
     * @param start Запуск.
     * @param isDaemon Демонический поток?
     * @param isVirtual Виртуальный поток?
     * @param block Вызываемый в новом потоке код.
     */
    fun new(
        secureContext: SecureContext,
        classLoadingContext: ClassLoadingContext,
        name: String,
        start: Boolean = true,
        isDaemon: Boolean = false,
        isVirtual: Boolean = false,
        block: () -> Unit
    ): Thread {
        val builder = if (isVirtual) Thread.ofVirtual() else Thread.ofPlatform()
        val thread = builder.unstarted {
            ScopedValue.runWhere(SECURE_CONTEXT, secureContext) {
                ScopedValue.runWhere(CLASSLOADING_CONTEXT, classLoadingContext) {
                    block()
                }
            }
        }
        thread.contextClassLoader = classLoadingContext.loader.asClassLoader
        thread.name = name
        thread.isDaemon = isDaemon
        if (start) thread.start()
        return thread
    }

    /**
     * Получение текущего контекста загрузки потока.
     *
     * @return Контекст.
     * @throws ThreadWithoutContextException Поток не привязан к контексту.
     */
    @JvmStatic
    @Throws(ThreadWithoutContextException::class)
    fun currentClassLoadingContext(): ClassLoadingContext {
        try {
            return CLASSLOADING_CONTEXT.get()
        } catch (_: NoSuchElementException) {
            throw ThreadWithoutContextException()
        }
    }

    /**
     * Получение текущего контекста безопасности потока.
     *
     * @return Контекст.
     * @throws ThreadWithoutContextException Поток не привязан к контексту.
     */
    @JvmStatic
    @Throws(ThreadWithoutContextException::class)
    fun currentSecureContext(): SecureContext {
        try {
            return SECURE_CONTEXT.get()
        } catch (_: NoSuchElementException) {
            throw ThreadWithoutContextException()
        }
    }

    /**
     * Ошибка получения контекста из потока.
     */
    class ThreadWithoutContextException : VTLRuntimeException()
}
