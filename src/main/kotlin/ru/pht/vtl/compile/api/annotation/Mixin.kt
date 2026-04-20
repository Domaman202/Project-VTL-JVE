package ru.pht.vtl.ru.pht.vtl.compile.api.annotation

import ru.pht.vtl.compile.api.annotation.utils.RefClass

/**
 * Создание миксина VTL.
 */
annotation class Mixin(
    /**
     * Имя миксина VTL.
     *
     * По умолчанию: Используется имя из JVM.
     */
    val name: String = "",

    /**
     * Целевой класс VTL.
     */
    val target: RefClass
) {
    /**
     * Миксин родителей.
     */
    annotation class Parents(
        /**
         * Режим миксина.
         */
        val mode: Mode,

        /**
         * Список родителей.
         */
        val parents: Array<RefClass>
    ) {
        enum class Mode {
            OVERWRITE,
            EXPAND,
            NARROW
        }
    }

    /**
     * Миксин поля.
     */
    annotation class Field(
        /**
         * Режим миксина.
         */
        val mode: Mode,

        /**
         * Имя поля VTL.
         *
         * По умолчанию: Используется имя из JVM.
         */
        val name: String = "",

        /**
         * Тип поля VTL.
         *
         * По умолчанию: Используется преобразованный из JVM.
         */
        val type: RefClass = RefClass()
    ) {
        enum class Mode {
            NEW,
            DELETE,
            OVERWRITE
        }
    }

    /**
     * Миксин метода.
     */
    annotation class Method(
        /**
         * Режим миксина.
         */
        val mode: Mode,

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
        val returnType: RefClass = RefClass()
    ) {
        enum class Mode {
            NEW,
            DELETE,
            OVERWRITE
        }
    }
}
