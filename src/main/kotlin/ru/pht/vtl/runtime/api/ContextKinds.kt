package ru.pht.vtl.ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.api.annotation.utils.RefContext

/**
 * Дочерние контексты.
 */
class ContextKinds(
    /**
     * Разрешение определения дочерних контекстов.
     */
    val allow: Boolean,

    /**
     * Список дочерних возможных контекстов.
     */
    val list: Array<RefContext>
)