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
            val find = this.findLoadedClassInContext(name)
            if (find != null)
                return find // Возвращаем найденный
            // Проверка защиты
            val secureCtx = ContextThread.currentSecureContext()
            if (isProtected && name.substring(0, name.lastIndexOf('.')) == secureCtx.name)
                throw ClassDefinitionException("Загрузка класса \"$name\" не может быть безопасна для контекста \"${secureCtx.displayedName}\"")
            // Загрузка без применения примесей
            return super.loadClass(name, resolve)
        } else {
            // ! Если примеси есть
            // Поиск уже загруженного с текущего загрузчика
            val find = this.findLoadedClassInContext(name)
            if (find != null && find.classLoader == this)
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
            TODO()
        }
    }

    /**
     * Поиск загруженного класса.
     *
     * @param name Имя класса.
     * @return `Класс` - если найден, `null` - иначе.
     */
    private fun findLoadedClassInContext(name: String): Class<*>? {
        this.findLoadedClass(name)?.let { return it }
        this.context.parents.firstNotNullOfOrNull { it.classLoader.findLoadedClass(name) }?.let { return it }
        return null
    }

    /**
     * Ошибка безопасности вызванная созданием контекста.
     */
    class ClassDefinitionException(message: String?) : VTLSecurityException(message)
}