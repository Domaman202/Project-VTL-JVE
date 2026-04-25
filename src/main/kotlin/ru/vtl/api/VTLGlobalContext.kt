package ru.pht.vtl.ru.vtl.api

import ru.pht.vtl.compile.api.annotation.Context
import ru.pht.vtl.compile.api.annotation.utils.ContextKinds
import ru.pht.vtl.compile.api.annotation.utils.RefContext

@Context(
    "global",
    parent = RefContext(),
    kinds = ContextKinds(allow = true, list = []),
    visibleFor = [RefContext(clazz = VTLGlobalContext::class)],
    mixinsFor = [RefContext(clazz = VTLGlobalContext::class)]
)
class VTLGlobalContext {
}