package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.OpenModifier
import ru.pht.vtl.compile.api.annotation.utils.RefClass
import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Создание класса VTL.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Class(
    /**
     * Имя класса VTL.
     *
     * По умолчанию: Используется имя из JVM.
     */
    val name: String = "",

    /**
     * Наследование класса & интерфейсов.
     *
     * По умолчанию: Используются наследования из JVM.
     */
    val parents: Array<RefClass> = [],

    /**
     * Возможность наследования.
     *
     * По умолчанию: Используется из JVM.
     */
    val open: OpenModifier = OpenModifier.UNKNOWN,

    /**
     * Привязка к контексту.
     *
     * По умолчанию: Глобальный.
     */
    val context: RefContext = RefContext("global"),
)
