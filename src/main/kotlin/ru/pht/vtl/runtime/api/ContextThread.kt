package ru.pht.vtl.ru.pht.vtl.runtime.api

import ru.pht.vtl.ru.pht.vtl.runtime.exception.VTLRuntimeException


/**
 * Утилиты для работы с потоками с привязкой к контексту.
 */
object ContextThread {
    private val CONTEXT = ScopedValue.newInstance<Context>()

    /**
     * Создание нового потока с привязкой к контексту.
     *
     * @param context Контекст.
     * @param name Имя потока.
     * @param start Запуск.
     * @param isDaemon Демонический поток?
     * @param isVirtual Виртуальный поток?
     * @param block Вызываемый в новом потоке код.
     */
    fun new(
        context: Context,
        name: String,
        start: Boolean = true,
        isDaemon: Boolean = false,
        isVirtual: Boolean = false,
        block: () -> Unit
    ): Thread {
        val builder = if (isVirtual) Thread.ofVirtual() else Thread.ofPlatform()
        val thread = builder.unstarted { ScopedValue.runWhere(CONTEXT, context) { block() } }
        thread.contextClassLoader = context.classLoader
        thread.name = name
        thread.isDaemon = isDaemon
        if (start) thread.start()
        return thread
    }

    /**
     * Получение контекста потока.
     *
     * @return Контекст.
     * @throws ThreadWithoutContextException Поток не привязан к контексту.
     */
    @JvmStatic
    @Throws(ThreadWithoutContextException::class)
    fun context(): Context {
        try {
            return CONTEXT.get()
        } catch (_: NoSuchElementException) {
            throw ThreadWithoutContextException()
        }
    }

    /**
     * Ошибка получения контекста из потока.
     */
    class ThreadWithoutContextException : VTLRuntimeException()
}
