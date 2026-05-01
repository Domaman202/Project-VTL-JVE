package ru.pht.vtl.runtime.api

/**
 * Runtime контекст загрузки классов VTL.
 *
 * @param name Имя контекста.
 * @param parents Родительские контексты.
 * @param classLoader Загрузчик классов.
 */
open class ClassLoadingContext protected constructor(
    /**
     * Имя контекста.
     */
    val name: String,
    /**
     * Родительские контексты.
     */
    val parents: Array<ClassLoadingContext>,
    /**
     * Загрузчик классов.
     */
    val classLoader: ContextClassLoader
) {
    /**
     * Создание нового контекста загрузки.
     *
     * @param name Имя контекста.
     * @param parents Родительское контексты.
     */
    constructor(name: String, parents: Array<ClassLoadingContext>) : this(name, parents, ContextClassLoader(emptyArray(), null))

    /**
     * Глобальный контекст загрузки классов.
     */
    object GlobalClassLoadingContext : ClassLoadingContext(
        "global",
        emptyArray(),
        ContextClassLoader(emptyArray(), ClassLoadingContext::class.java.classLoader)
    )
}