package ru.pht.vtl.ru.pht.vtl.runtime.api

object ContextThread {
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

    @Throws(NoContextException::class)
    fun context(): Context {
        try {
            return CONTEXT.get()
        } catch (_: NoSuchElementException) {
            throw NoContextException()
        }
    }

    private val CONTEXT = ScopedValue.newInstance<Context>()
}