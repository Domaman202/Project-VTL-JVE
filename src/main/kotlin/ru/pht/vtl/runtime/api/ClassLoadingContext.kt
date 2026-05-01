package ru.pht.vtl.runtime.api

/**
 * Runtime контекст загрузки классов VTL.
 */
open class ClassLoadingContext {
    /**
     * Имя контекста.
     */
    val name: String

    /**
     * Родительский контекст.
     */
    val parent: ClassLoadingContext?

    /**
     * Загрузчик классов.
     */
    val classLoader: ContextClassLoader

    /**
     * Создание нового контекста загрузки.
     *
     * @param name Имя контекста.
     * @param parent Родительский контекст.
     */
    constructor(name: String, parent: ClassLoadingContext?) {
        this.name = name
        this.parent = parent
        this.classLoader = ContextClassLoader(this, emptyArray(), null)
    }

    /**
     * Создание нового контекста загрузки.
     *
     * @param name Имя контекста.
     * @param parent Родительский контекст.
     * @param classLoader Родительский загрузчик классов.
     */
    protected constructor(name: String, parent: ClassLoadingContext?, parentClassLoader: ClassLoader) {
        this.name = name
        this.parent = parent
        this.classLoader = ContextClassLoader(this, emptyArray(), parentClassLoader)
    }

    /**
     * Глобальный контекст загрузки классов.
     */
    object GlobalClassLoadingContext : ClassLoadingContext(
        "global",
        null,
        ClassLoadingContext::class.java.classLoader
    )

}