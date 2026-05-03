package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.ClassStmt
import ru.pht.vtl.compile.ast.InterfaceStmt
import ru.pht.vtl.compile.ast.StatementClass
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
    val loader: IContextClassLoader

    /**
     * Загруженные классы.
     */
    val loadedClasses: MutableList<Pair<Class<*>, SecureContext>>

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
        this.loader = ContextClassLoaderImpl(this, emptyArray(), null)
        this.loadedClasses = ArrayList()
    }

    /**
     * Создание нового контекста загрузки.
     *
     * @param name Имя контекста.
     * @param parent Родительский контекст.
     * @param parentClassLoader Родительский загрузчик классов.
     */
    protected constructor(name: String, parent: ClassLoadingContext?, parentClassLoader: ClassLoader) {
        this.name = name
        this.parent = parent
        this.mixins = HashMap()
        this.appliedMixins = HashMap()
        this.loader = ContextClassLoaderImpl(this, emptyArray(), parentClassLoader)
        this.loadedClasses = ArrayList()
    }

    fun defineClass(clazz: ClassStmt) {
        this.defineClass0(
            name = clazz.name,
            parents = clazz.parents,
            isInterface = false,
            isOpen = clazz.open,
            body = clazz.body,
            context = clazz.context
        )
    }

    fun defineClass(clazz: InterfaceStmt) {
        this.defineClass0(
            name = clazz.name,
            parents = clazz.parents,
            isInterface = true,
            isOpen = true,
            body = clazz.body,
            context = clazz.context
        )
    }

    protected fun defineClass0(name: String, parents: List<String>, isInterface: Boolean, isOpen: Boolean, body: List<StatementClass>, context: String) {
        val context = SecureContextPool.findOrThrow(context)
        val clazz = this.loader.defineClass(name, parents, isInterface, isOpen, body, context)
        this.loadedClasses.add(Pair(clazz, context))
    }

    fun findLoadedClass(name: String): Pair<Class<*>, SecureContext>? {
        this.loadedClasses.find { it.first.name == name }?.let { return it }
        return this.parent?.findLoadedClass(name)
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