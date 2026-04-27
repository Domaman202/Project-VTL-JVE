package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.api.annotation.utils.RefContext
import ru.pht.vtl.runtime.exception.VTLSecurityException

/**
 * Runtime контекст VTL.
 */
open class Context {
    /**
     * Техническое имя контекста.
     */
    val name: String

    /**
     * Отображаемое имя контекста.
     *
     * ! Используется при отладке.
     */
    val displayedName: String

    /**
     * Родительский контекст.
     */
    val parent: Context?

    /**
     * Дочерние контексты.
     */
    val kinds: ContextKinds

    /**
     * Загрузчик классов.
     */
    val classLoader: ContextClassLoader


    /**
     * Конструктор контекста.
     *
     * @param name Имя контекста.
     * @param displayedName Отображаемое имя контекста.
     * @param parent Родительский контекст.
     */
    protected constructor(name: String, displayedName: String?, parent: Context?, kinds: ContextKinds) {
        this.name = name
        this.displayedName = displayedName ?: this.name
        this.parent = parent
        this.kinds = kinds
        this.classLoader = ContextClassLoader(emptyArray(), parent?.classLoader)
    }

    /**
     * Безопасное создание нового контекста.
     *
     * @param module Модуль пытающийся создать контекст.
     * @param name Имя контекста.
     * @param displayedName Отображаемое имя контекста.
     * @param parent Родительский контекст.
     * @return Новый контекст.
     * @throws ContextCreationException Если имя контекста не начинается с имени модуля.
     * @throws ContextCreationException Если родительский контекст не может иметь дочерних контекстов или дочернего контекста с таким именем.
     */
    @Throws(ContextCreationException::class)
    fun of(module: String, name: String, displayedName: String?, parent: Context, kinds: ContextKinds): Context {
        if (!name.startsWith(module))
            throw ContextCreationException("Определение контекста \"${name}\" (\"${displayedName ?: name}\") небезопасно для модуля \"${module}\"")
        if (!parent.kinds.allow || (parent.kinds.list.isNotEmpty() && parent.kinds.list.none { it.name == name }))
            throw ContextCreationException("Контекст \"${name}\" не может быть дочерним для контекста \"${parent.name}\"")
        return Context(name, displayedName, parent, kinds)
    }

    /**
     * Глобальный контекст.
     */
    object GlobalContext : Context(
        "global",
        null,
        null,
        ContextKinds(true, emptyArray())
    )

    /**
     * Дочерние контексты.
     */
    class ContextKinds(
        /**
         * Разрешение определения дочерних контекстов.
         */
        val allow: Boolean,

        /**
         * Список дочерних возможных контекстов.
         */
        val list: Array<RefContext>
    )


    /**
     * Ошибка безопасности вызванная созданием контекста.
     */
    class ContextCreationException(message: String?) : VTLSecurityException(message)
}