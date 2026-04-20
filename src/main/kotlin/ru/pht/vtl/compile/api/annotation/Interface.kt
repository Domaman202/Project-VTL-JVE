package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.RefClass
import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Создание интерфейса VTL.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Interface(
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
