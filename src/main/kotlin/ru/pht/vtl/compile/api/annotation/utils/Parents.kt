package ru.pht.vtl.compile.api.annotation.utils

import ru.pht.vtl.compile.api.annotation.utils.RefClass

/**
 * Наследование.
 */
annotation class Parents(
    /**
     * Список наследования.
     */
    val list: Array<RefClass> = [],

    /**
     * Использование: `JVM` / `Список` / `Список иначе JVM`.
     * 
     * По умолчанию: `Список иначе JVM`.
     */
    val using: Using = Using.LIST_OR_JVM
) {
    enum class Using {
        /**
         * Получение родителей из JVM.
         */
        JVM,

        /**
         *  Получение родителей из списка.
         */
        LIST,

        /**
         * Получение родителей из списка, если он пуст, то из JVM.
         */
        LIST_OR_JVM
    }
}
