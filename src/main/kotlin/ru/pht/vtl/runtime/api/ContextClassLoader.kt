package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementMixin
import ru.pht.vtl.runtime.exception.VTLSecurityException
import java.net.URL
import java.net.URLClassLoader

/**
 * Загрузчик классов с привязкой к контексту.
 *
 * @param context Контекст загрузки.
 * @param urls URL для загрузки классов.
 * @param parent Родительский загрузчик классов.
 */
open class ContextClassLoader(
    val context: ClassLoadingContext,
    urls: Array<URL>,
    parent: ClassLoader?
) : URLClassLoader(urls, parent) {
    /**
     * Список не применённых примесей.
     */
    val mixins: MutableMap<String, MutableList<Pair<SecureContext, StatementMixin>>> = HashMap()

    /**
     * Список применённых примесей.
     */
    val appliedMixins: MutableMap<Class<*>, List<StatementMixin>> = HashMap()

    override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        // Проверка на защищённый класс
        val isProtected = name.startsWith('@')
        val name = name.substring(1)
        // Проверка наличия примесей
        val mixins = this.mixins[name]
            ?: return super.loadClass(name, resolve) // Загружаем без применения примесей
        if (mixins.isEmpty()) {
            // ! Если примесей нет
            // Поиск уже загруженного
            val find = this.findLoadedClass(name)
            if (find != null)
                return find // Возвращаем найденный
            // Проверка защиты
            val secureCtx = ContextThread.currentSecureContext()
            if (isProtected && name.substring(0, name.lastIndexOf('.')) == secureCtx.name)
                throw ClassDefinitionException("Загрузка класса \"$name\" не может быть безопасна для контекста \"${secureCtx.displayedName}\"")
            // Загрузка без применения примесей
            var definer = this.context
            while (definer.parent != null)
                definer = definer.parent
            return definer.classLoader.defineClassWithoutMixins(name)
        } else {
            // ! Если примеси есть
            // Поиск уже загруженного и проверка применения нужных примесей
            val find = this.findLoadedClass(name)
            if (find != null && (find.classLoader == this || (find.classLoader is ContextClassLoader && (find.classLoader as ContextClassLoader).appliedMixins.contains(find))))
                return find // Возвращаем найденный
            // Проверка защиты
            val secureCtx = ContextThread.currentSecureContext()
            if (isProtected && name.substring(0, name.lastIndexOf('.')) == secureCtx.name)
                throw ClassDefinitionException("Загрузка класса \"$name\" не может быть безопасна для контекста \"${secureCtx.displayedName}\"")
            // Проверка разрешения примесей
            val invalidMixins = mixins
                .asSequence()
                .filter { !it.first.checkMixinAllow(secureCtx) }
                .map { it.first.displayedName }
                .toList()
            if (invalidMixins.isNotEmpty())
                throw ClassDefinitionException("Невозможно безопасно применить примеси из контекстов $invalidMixins для класса \"$name\"")
            // Загрузка с применением примесей
            return this.defineClassWithMixins(name, mixins)
        }
    }

    protected fun defineClassWithoutMixins(name: String): Class<*> {
        TODO()
    }

    protected fun defineClassWithMixins(name: String, mixins: List<Pair<SecureContext, StatementMixin>>): Class<*> {
        TODO()
    }

    /**
     * Ошибка безопасности вызванная созданием контекста.
     */
    class ClassDefinitionException(message: String?) : VTLSecurityException(message)
}