package ru.pht.vtl.runtime.api

import ru.pht.vtl.compile.ast.StatementMixin
import java.net.URL
import java.net.URLClassLoader

open class ContextClassLoader(urls: Array<URL>, parent: ClassLoader?) : URLClassLoader(urls, parent) {
    /**
     * Список не применённых миксинов.
     */
    val mixins: MutableMap<String, MutableList<StatementMixin>> = HashMap()

    override fun loadClass(name: String?, resolve: Boolean): Class<*>? {
        // Поиск уже загруженного
        val find = this.findLoadedClass(name)
        if (find != null)
            return find // Возвращаем найденный
        // Проверка миксинов
        val mixins = this.mixins[name]
            ?: return super.loadClass(name, resolve) // Загружаем без применения миксинов
        // Загрузка с применением миксинов
        TODO()
    }
}