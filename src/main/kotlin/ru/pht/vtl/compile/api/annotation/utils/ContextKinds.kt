package ru.pht.vtl.compile.api.annotation.utils

/**
 * Дочерние контексты.
 */
annotation class ContextKinds(
    /**
     * Разрешение определения дочерних контекстов.
     *
     * По умолчанию: Разрешено.
     */
    val allow: Boolean = true,

    /**
     * Список дочерних возможных контекстов.
     *
     * По умолчанию: Любые.
     */
    val list: Array<RefContext> = []
)
