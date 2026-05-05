package ru.pht.vtl.compile

import ru.pht.vtl.compile.api.annotation.Class
import ru.pht.vtl.compile.ast.utils.NodePrinter
import kotlin.test.Test
import kotlin.test.assertEquals

class CompilerTest {
    @Test
    fun simpleTest() {
        val bytes = CompilerTest::class.java.classLoader.getResourceAsStream($$"ru/pht/vtl/compile/CompilerTest$SimpleTest.class")?.readAllBytes() ?: error("Class file not found")
        val node = Compiler().compile(bytes)
        val nodePrint = NodePrinter.print(node)
        assertEquals(
            """
                
            """.trimIndent(),
            nodePrint
        )
    }

    @Class(name = "ru/DmN/SimpleTest")
    class SimpleTest
}