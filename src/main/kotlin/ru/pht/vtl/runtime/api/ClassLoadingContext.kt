package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementMixin

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
     * Список не применённых примесей.
     */
    val mixins: MutableMap<String, MutableList<Pair<SecureContext, StatementMixin>>>

    /**
     * Список применённых примесей.
     */
    val appliedMixins: MutableMap<Class<*>, List<StatementMixin>>

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
        this.mixins = HashMap()
        this.appliedMixins = HashMap()
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
        this.mixins = HashMap()
        this.appliedMixins = HashMap()
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