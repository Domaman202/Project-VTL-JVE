package ru.pht.vtl.ru.pht.vtl.runtime.api

open class Context(
    val name: String,
    val parent: Context?,
) {
    val classLoader: ClassLoader = ContextClassLoader(emptyArray(), parent?.classLoader)

    object GlobalContext : Context("global", null)
}