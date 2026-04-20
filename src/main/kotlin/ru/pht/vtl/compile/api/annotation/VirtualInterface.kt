package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.RefClass
import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Описание внешнего интерфейса VTL.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class VirtualInterface(
    /**
     * Имя интерфейса VTL.
     *
     * По умолчанию: Используется имя из JVM.
     */
    val name: String = "",

    /**
     * Наследование интерфейсов.
     *
     * По умолчанию: Используются наследования из JVM.
     */
    val parents: Array<RefClass> = [],

    /**
     * Привязка к контексту.
     *
     * По умолчанию: Глобальный.
     */
    val context: RefContext = RefContext("global"),
)
