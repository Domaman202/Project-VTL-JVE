package ru.pht.vtl.compile.api.annotation.utils

import kotlin.reflect.KClass

/**
 * Ссылка на класс VTL.
 */
annotation class RefClass(
    /**
     * Ссылка по имени VTL.
     */
    val name: String = "",

    /**
     * Ссылка по классу JVM.
     */
    val clazz: KClass<*> = Void::class
)
