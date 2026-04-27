package ru.pht.vtl.runtime.translator

import java.nio.file.Path

interface IModuleProvider {
    fun resolve(path: Path): IModule?
}