package ru.pht.vtl.compile.api.annotation.utils

import kotlin.reflect.KClass

/**
 * Ссылка на контекст VTL.
 */
annotation class RefContext(
    /**
     * Ссылка по имени VTL.
     */
    val name: String = "",

    /**
     * Ссылка по классу JVM.
     */
    val clazz: KClass<*> = Void::class,
)
