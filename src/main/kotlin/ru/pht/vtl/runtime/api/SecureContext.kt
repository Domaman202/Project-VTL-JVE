package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.api.annotation.utils.RefContext
import ru.pht.vtl.runtime.exception.VTLSecurityException

/**
 * Runtime контекст безопасности VTL.
 */
open class SecureContext {
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
    val parent: SecureContext?

    /**
     * Дочерние контексты.
     */
    val kinds: ContextKinds

    /**
     * Видимость для других контекстов.
     */
    val visibleFor: Array<String>?

    /**
     * Возможность примеси из других контекстов.
     */
    val mixinsFor: Array<String>?


    /**
     * Конструктор контекста.
     *
     * @param name Имя контекста.
     * @param displayedName Отображаемое имя контекста.
     * @param parent Родительский контекст.
     * @param kinds Дочерние контексты.
     * @param visibleFor Видимость для других контекстов.
     * @param mixinsFor Возможность примеси из других контекстов.
     */
    protected constructor(name: String, displayedName: String?, parent: SecureContext?, kinds: ContextKinds, visibleFor: Array<String>?, mixinsFor: Array<String>?) {
        this.name = name
        this.displayedName = displayedName ?: this.name
        this.parent = parent
        this.kinds = kinds
        this.visibleFor = visibleFor ?: parent?.visibleFor
        this.mixinsFor = mixinsFor ?: parent?.mixinsFor
    }

    /**
     * Безопасное создание нового контекста.
     *
     * @param module Модуль пытающийся создать контекст.
     * @param name Имя контекста.
     * @param displayedName Отображаемое имя контекста.
     * @param parent Родительский контекст.
     * @param visibleFor Видимость для других контекстов.
     * @param mixinsFor Возможность примеси из других контекстов.
     * @return Новый контекст.
     * @throws ContextCreationException Если имя контекста не начинается с имени модуля.
     * @throws ContextCreationException Если родительский контекст не может иметь дочерних контекстов или дочернего контекста с таким именем.
     */
    @Throws(ContextCreationException::class)
    fun of(module: String, name: String, displayedName: String?, parent: SecureContext, kinds: ContextKinds, visibleFor: Array<String>?, mixinsFor: Array<String>?): SecureContext {
        if (!name.startsWith(module))
            throw ContextCreationException("Определение контекста \"$name\" (\"${displayedName ?: name}\") небезопасно для модуля \"$module\"")
        if (!parent.kinds.allow || (parent.kinds.list.isNotEmpty() && parent.kinds.list.none { it.name == name }))
            throw ContextCreationException("Контекст \"$name\" не может быть дочерним для контекста \"${parent.name}\"")
        return SecureContext(name, displayedName, parent, kinds, visibleFor, mixinsFor)
    }

    /**
     * Проверка возможности применения миксинов из контекста.
     *
     * @param from Контекст желающий применить миксины.
     * @return `true` - если применение разрешено, `false` - иначе.
     */
    fun checkMixinAllow(from: SecureContext): Boolean {
        if (this == from)
            return true
        if (this.mixinsFor == null || this.mixinsFor.contains(from.name))
            return true
        if (this.parent?.let(this::checkMixinAllow) == true)
            return true
        return false
    }

    /**
     * Глобальный контекст безопасности.
     */
    object GlobalSecureContext : SecureContext(
        "global",
        null,
        null,
        ContextKinds(true, emptyArray()),
        null,
        null
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