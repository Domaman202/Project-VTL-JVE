package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementClass
import ru.pht.vtl.compile.ast.StatementMixin
import ru.pht.vtl.runtime.exception.VTLSecurityException
import java.net.URL
import java.net.URLClassLoader

/**
 * Реализация загрузчика классов VTL поверх [java.net.URLClassLoader].
 *
 * @param context Контекст загрузки.
 * @param urls URL для загрузки классов.
 * @param parent Родительский загрузчик классов.
 */
open class ContextClassLoaderImpl(
    val context: ClassLoadingContext,
    urls: Array<URL>,
    parent: ClassLoader?
) : URLClassLoader(urls, parent), IContextClassLoader {
    override fun defineClass(name: String, parents: List<String>, isInterface: Boolean, isOpen: Boolean, body: List<StatementClass>, context: SecureContext): Class<*> {
        synchronized(this.getClassLoadingLock(name)) {
            // Проверка на защищённый класс
            val isProtected = name.startsWith('@')
            val name = name.substring(1)
            // Проверка защиты
            val secureCtx = ContextThread.currentSecureContext()
            if (isProtected) {
                // Проверка определения в одноимённом контексте
                if (name.substring(0, name.lastIndexOf('.')) != secureCtx.name) {
                    throw ClassDefinitionException("Определение класса \"$name\" невозможно из контекста \"${secureCtx.displayedName}")
                }
            } else {
                // Проверка видимости контекста
                if (!context.checkAccessAllow(secureCtx)) {
                    throw ClassDefinitionException("Доступ класса \"$name\" невозможен из контекста \"${secureCtx.displayedName}")
                }
            }
            // Проверка наличия примесей
            val mixins = this.context.mixins[name]
            if (mixins.isNullOrEmpty()) {
                // ! Если примесей нет
                // Определение без применения примесей
                var definer = this.context
                while (definer.parent != null)
                    definer = definer.parent
                return definer.loader.defineClass0(name, parents, isProtected, isInterface, isOpen, body, context, null)
            } else {
                // ! Если примеси есть
                // Проверка разрешения примесей
                val invalidMixins = mixins
                    .asSequence()
                    .filter { !it.first.checkMixinAllow(secureCtx) }
                    .map { it.first.displayedName }
                    .toList()
                if (invalidMixins.isNotEmpty())
                    throw ClassDefinitionException("Невозможно безопасно применить примеси из контекстов $invalidMixins для класса \"$name\"")
                // Загрузка с применением примесей
                return this.defineClass0(name, parents, isProtected, isInterface, isOpen, body, context, mixins)
            }
        }
    }

    override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        synchronized(this.getClassLoadingLock(name)) {
            // Проверка "java.*" классов. (Системные классы языка Java разрешены)
            if (name.startsWith("java."))
                return super.loadClass(name, resolve)
            // Загрузка VTL классов.
            val find = this.context.findLoadedClass(name)
                ?: throw ClassNotFoundException("Класс \"$name\" не найден в контексте \"${this.context.name}\"")
            // Проверка видимости класса.
            val secureCtx = ContextThread.currentSecureContext()
            if (!find.second.checkAccessAllow(secureCtx))
                throw ClassNotFoundException("Доступ класса \"$name\" невозможен из контекста \"${secureCtx.displayedName}")
            // Успех
            return find.first
        }
    }

    override fun defineClass0(name: String, parents: List<String>, isProtected: Boolean, isInterface: Boolean, isOpen: Boolean, body: List<StatementClass>, context: SecureContext, mixins: List<Pair<SecureContext, StatementMixin>>?): Class<*> {
        TODO("Not yet implemented")
    }

    /**
     * Ошибка безопасности вызванная созданием контекста.
     */
    class ClassDefinitionException(message: String?) : VTLSecurityException(message)
}