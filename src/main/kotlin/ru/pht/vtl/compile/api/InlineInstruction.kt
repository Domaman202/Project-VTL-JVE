package ru.pht.vtl.compile.api

/**
 * Методы заменяемые на инструкции VTL.
 */
class InlineInstruction {
    /**
     * Инструкция `call-int`.
     *
     * Производит вызов предоставляемого системой API.
     *
     * @param name Внутренняя функция для вызова.
     * @param args Аргументы вызова.
     * @return Возвращаемое значение.
     */
    fun callInternal(name: String, args: Array<Any?>): Any? = throw NotInlinedInstruction()

    /**
     * Инструкция `set-obj`
     *
     * Производит установку значения поля в объекте.
     *
     * @param instance Объект.
     * @param name Поле.
     * @param value Значение.
     */
    fun setObject(instance: Any, name: String, value: Any?): Unit = throw NotInlinedInstruction()

    /**
     * Инструкция `set-static`
     *
     * Производит установку значения статического поля класса.
     *
     * @param clazz Класс.
     * @param name Поле.
     * @param value Значение.
     */
    fun setStatic(clazz: String, name: String, value: Any?): Unit = throw NotInlinedInstruction()


    /**
     * Инструкция `get-obj`
     *
     * Производит получение значения поля из объекта.
     *
     * @param instance Объект.
     * @param name Поле.
     * @return Значение поля.
     */
    fun getObject(instance: Any, name: String): Any? = throw NotInlinedInstruction()

    /**
     * Инструкция `get-static`
     *
     * Производит получение значения статического поля класса.
     *
     * @param clazz Класс.
     * @param name Поле.
     * @return Значение поля.
     */
    fun getStatic(clazz: String, name: String): Any? = throw NotInlinedInstruction()
    
    class NotInlinedInstruction internal constructor() : RuntimeException()
}