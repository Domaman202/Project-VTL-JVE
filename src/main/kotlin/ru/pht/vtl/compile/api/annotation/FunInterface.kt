package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.Parents
import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Создание функционального интерфейса VTL.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class FunInterface(
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
    val parents: Parents = Parents(),

    /**
     * Привязка к контексту.
     *
     * По умолчанию: Глобальный.
     */
    val context: RefContext = RefContext("global"),
)
