package ru.pht.vtl.ru.pht.vtl.runtime.translator

import io.github.z4kn4fein.semver.Version
import java.io.InputStream
import java.nio.file.Path

interface IModule {
    val name: String
    val version: Version

    val dependencies: List<IModule>

    val sources: List<IFile>
    val resources: List<IFile>

    interface IFile {
        val path: Path

        fun openStream(): InputStream
    }
}