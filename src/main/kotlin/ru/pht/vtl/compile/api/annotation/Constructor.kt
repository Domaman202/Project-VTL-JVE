package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.RefClass

/**
 * Конструктор метода VTL.
 */
@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.BINARY)
annotation class Constructor(
    /**
     * Типы аргументов метода VTL.
     *
     * По умолчанию: Используются преобразованные из JVM.
     */
    val argumentTypes: Array<RefClass> = [],
)
