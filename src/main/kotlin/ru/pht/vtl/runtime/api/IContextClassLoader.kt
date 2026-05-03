package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementClass
import ru.pht.vtl.compile.ast.StatementMixin
import ru.pht.vtl.runtime.api.ContextClassLoaderImpl.ClassDefinitionException

/**
 * Загрузчик классов VTL.
 *
 * ! Всегда должен наследоваться от [java.lang.ClassLoader].
 */
interface IContextClassLoader {
    /**
     * Получить объект как [java.lang.ClassLoader].
     */
    val asClassLoader: ClassLoader get() = this as ClassLoader

    /**
     * Определение VTL класса с проверками.
     * Используется в [ClassLoadingContext].
     *
     * @param name Сырое имя.
     * @param parents Список родителей.
     * @param isInterface Это интерфейс?
     * @param isOpen Можно наследовать?
     * @param body Тело.
     * @param context Контекст безопасности класса.
     * @return Java класс.
     * @throws ClassDefinitionException
     */
    fun defineClass(name: String, parents: List<String>, isInterface: Boolean, isOpen: Boolean, body: List<StatementClass>, context: SecureContext): Class<*>

    /**
     * Определение VTL класса без проверок.
     * Используется в [IContextClassLoader].
     *
     * @param name Сырое имя.
     * @param parents Список родителей.
     * @param isProtected Это защищённый класс?
     * @param isInterface Это интерфейс?
     * @param isOpen Можно наследовать?
     * @param body Тело.
     * @param context Контекст безопасности класса.
     * @param mixins Примеси.
     * @return Java класс.
     * @throws ClassDefinitionException
     */
    fun defineClass0(name: String, parents: List<String>, isProtected: Boolean, isInterface: Boolean, isOpen: Boolean, body: List<StatementClass>, context: SecureContext, mixins: List<Pair<SecureContext, StatementMixin>>?): Class<*>
}

/**
 * Попытка получить объект как [IContextClassLoader].
 */
val ClassLoader.tryAsContextClassLoader get() = if (this is IContextClassLoader) this as IContextClassLoader else null