package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementMixin
import ru.pht.vtl.runtime.exception.VTLSecurityException
import java.net.URL
import java.net.URLClassLoader

open class ContextClassLoader(urls: Array<URL>, parent: ClassLoader?) : URLClassLoader(urls, parent) {
    /**
     * Список не применённых примесей.
     */
    val mixins: MutableMap<String, MutableList<Pair<Context, StatementMixin>>> = HashMap()

    override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        // Проверка на защищённый класс
        val isProtected = name.startsWith('@')
        val name = name.substring(1)
        // Поиск уже загруженного
        val find = this.findLoadedClass(name)
        if (find != null)
            return find // Возвращаем найденный
        // Проверка защиты
        val context = ContextThread.currentContext()
        if (isProtected && name.substring(0, name.lastIndexOf('.')) == context.name)
            throw ClassDefinitionException("Загрузка класса \"$name\" не может быть безопасна для контекста \"${context.displayedName}\"")
        // Проверка наличия примесей
        val mixins = this.mixins[name]
            ?: return super.loadClass(name, resolve) // Загружаем без применения примесей
        // Проверка разрешения примесей
        val invalidMixins = mixins
            .asSequence()
            .filter { !it.first.checkMixinAllow(context) }
            .map { it.first.displayedName }
            .toList()
        if (invalidMixins.isNotEmpty())
            throw ClassDefinitionException("Невозможно безопасно применить примеси из контекстов $invalidMixins для класса \"$name\"")
        // Загрузка с применением примесей
        TODO()
    }

    /**
     * Ошибка безопасности вызванная созданием контекста.
     */
    class ClassDefinitionException(message: String?) : VTLSecurityException(message)
}