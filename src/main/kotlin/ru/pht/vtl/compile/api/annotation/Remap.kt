package ru.pht.vtl.ru.pht.vtl.compile.api.annotation

import kotlin.reflect.KClass

/**
 * Перенаправление классов JVM в VTL.
 */
annotation class Remap(
    /**
     * Имя класса VTL.
     *
     * По умолчанию: Используется имя из JVM.
     */
    val name: String = "",

    /**
     * Перенаправляемые JVM классы.
     */
    val from: Array<KClass<*>>
)
