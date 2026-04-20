package ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.AbstractModifier
import ru.pht.vtl.compile.api.annotation.utils.OpenModifier
import ru.pht.vtl.compile.api.annotation.utils.RefClass
import ru.pht.vtl.compile.api.annotation.utils.StaticModifier

/**
 * Создание метода VTL.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Method(
    /**
     * Имя метода VTL.
     *
     * По умолчанию: Используется имя из JVM.
     */
    val name: String = "",

    /**
     * Типы аргументов метода VTL.
     *
     * По умолчанию: Используются преобразованные из JVM.
     */
    val argumentTypes: Array<RefClass> = [],

    /**
     * Возвращаемый тип метода VTL.
     *
     * По умолчанию: Используется преобразованный из JVM.
     */
    val returnType: RefClass = RefClass(),

    /**
     * Возможность переопределения.
     *
     * По умолчанию: Используется из JVM.
     */
    val open: OpenModifier = OpenModifier.UNKNOWN,

    /**
     * Статичность.
     *
     * По умолчанию: Используется из JVM.
     */
    val static: StaticModifier = StaticModifier.UNKNOWN,


    /**
     * Абстрактность.
     *
     * По умолчанию: Используется из JVM.
     */
    val abstract: AbstractModifier = AbstractModifier.UNKNOWN,
)
