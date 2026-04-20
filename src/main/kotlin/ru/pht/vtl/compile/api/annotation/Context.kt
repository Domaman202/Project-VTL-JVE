package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.ContextKinds
import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Создание контекста VTL.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Context(
    /**
     * Имя контекста VTL.
     *
     * Должно `начинаться с` / `полностью соответствовать` имени модуля в котором определяется контекст.
     */
    val name: String,

    /**
     * Родительский контекст.
     *
     * По умолчанию: Глобальный.
     */
    val parent: RefContext = RefContext("global"),

    /**
     * Дочерние контексты.
     */
    val kinds: ContextKinds = ContextKinds(),

    /**
     * Видимость для других контекстов.
     *
     * По умолчанию: Наследование от родителя (Глобальный).
     */
    val visibleFor: Array<RefContext> = [RefContext("global")],

    /**
     * Возможность примеси из других контестов.
     *
     * По умолчанию: Наследование от родителя (Глобальный).
     */
    val mixinsFor: Array<RefContext> = [RefContext("global")]
)
