package ru.pht.vtl.compile.ast.utils

import org.junit.jupiter.api.assertThrows
import ru.pht.vtl.compile.api.annotation.Mixin
import ru.pht.vtl.compile.ast.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NodePrinterTest {
    @Test
    fun testUnknownNodeType() {
        assertThrows<IllegalArgumentException> { NodePrinter.print(Node()) }
        assertThrows<IllegalArgumentException> { NodePrinter.print(Statement()) }
        assertThrows<IllegalArgumentException> { NodePrinter.print(StatementClass()) }
        assertThrows<IllegalArgumentException> { NodePrinter.print(StatementMixin()) }
        assertThrows<IllegalArgumentException> { NodePrinter.print(Expression()) }
    }

    @Test
    fun testBlockStmt() {
        assertEquals(
            """
                [Block
                |	(Body):
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                BlockStmt(
                    listOf()
                )
            )
        )
        assertEquals(
            """
                [Block
                |	(Body):
                |	|	[Call/Internal
                |	|	|	(Name): vtl/core/println
                |	|	|	(Arguments):
                |	|	|	|	[Value
                |	|	|	|	|	(Type): STRING
                |	|	|	|	|	(Value): Первый!
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	|	[Call/Internal
                |	|	|	(Name): vtl/core/println
                |	|	|	(Arguments):
                |	|	|	|	[Value
                |	|	|	|	|	(Type): STRING
                |	|	|	|	|	(Value): Второй!
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                BlockStmt(
                    listOf(
                        CallExpr.Internal(
                            "vtl/core/println",
                            listOf(
                                ValueExpr(
                                    ValueExpr.Type.STRING,
                                    "Первый!"
                                )
                            )
                        ),
                        CallExpr.Internal(
                            "vtl/core/println",
                            listOf(
                                ValueExpr(
                                    ValueExpr.Type.STRING,
                                    "Второй!"
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testCallInternalExpr() {
        assertEquals(
            """
                [Call/Internal
                |	(Name): vtl/core/println
                |	(Arguments):
                |	|	[Value
                |	|	|	(Type): STRING
                |	|	|	(Value): a + 
                |	|	]
                |	|	[Value
                |	|	|	(Type): STRING
                |	|	|	(Value): b = 
                |	|	]
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 33
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Internal(
                    "vtl/core/println",
                    listOf(
                        ValueExpr(
                            ValueExpr.Type.STRING,
                            "a + "
                        ),
                        ValueExpr(
                            ValueExpr.Type.STRING,
                            "b = "
                        ),
                        ValueExpr(
                            ValueExpr.Type.INT,
                            33
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testCallVirtualExpr() {
        assertEquals(
            """
                [Call/Virtual
                |	(Name): add
                |	(Instance):
                |	|	[Value
                |	|	|	(Type): STRING
                |	|	|	(Value): i = 
                |	|	]
                |	-
                |	(Arguments):
                |	|	[Value
                |	|	|	(Type): STRING
                |	|	|	(Value): 123
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Virtual(
                    "add",
                    listOf(
                        ValueExpr(
                            ValueExpr.Type.STRING,
                            "123"
                        )
                    ),
                    ValueExpr(
                        ValueExpr.Type.STRING,
                        "i = "
                    )
                )
            )
        )
    }

    @Test
    fun testCallSuperExpr() {
        assertEquals(
            """
                [Call/Super
                |	(Name): toString
                |	(Class): vtl/core/Object
                |	(Arguments):
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Super(
                    "toString",
                    listOf(),
                    "vtl/core/Object"
                )
            )
        )
    }

    @Test
    fun testCallStaticExpr() {
        assertEquals(
            """
                [Call/Static
                |	(Name): foo
                |	(Class): ru/DmN/Foo
                |	(Arguments):
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Static(
                    "foo",
                    listOf(),
                    "ru/DmN/Foo"
                )
            )
        )
    }

    @Test
    fun testCallDynamicExpr() {
        assertEquals(
            """
                [Call/Dynamic
                |	(Name): bar
                |	(Instance):
                |	|	[VarGet
                |	|	|	(Name): o
                |	|	]
                |	-
                |	(Arguments):
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Dynamic(
                    "bar",
                    listOf(),
                    VarGetExpr(
                        "o"
                    )
                )
            )
        )
        assertEquals(
            """
                [Call/Dynamic
                |	(Name): bar
                |	(Instance):
                |	|	[Value
                |	|	|	(Type): TYPE
                |	|	|	(Value): ru/DmN/Bar
                |	|	]
                |	-
                |	(Arguments):
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                CallExpr.Dynamic(
                    "bar",
                    listOf(),
                    ValueExpr(
                        ValueExpr.Type.TYPE,
                        "ru/DmN/Bar"
                    )
                )
            )
        )
    }

    @Test
    fun testClassStmt() {
        assertEquals(
            """
                [Class
                |	(Name): ru/DmN/test/FooImpl
                |	(Parents): [vtl/core/Object, ru/DmN/test/IFoo]
                |	(Open): false
                |	(Context): ru/dmn/test
                |	(Body):
                |	|	[Method
                |	|	|	(Arguments): []
                |	|	|	(ReturnType): vtl/core/Void
                |	|	|	(Body):
                |	|	|	|	[Return
                |	|	|	|	|	(Value): (None)
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                ClassStmt(
                    "ru/DmN/test/FooImpl",
                    listOf("vtl/core/Object", "ru/DmN/test/IFoo"),
                    false,
                    "ru/dmn/test",
                    listOf(
                        MethodStmt(
                            "foo",
                            listOf(),
                            "vtl/core/Void",
                            listOf(
                                ReturnStmt(
                                    null
                                )
                            ),
                        ),
                    )
                )
            )
        )
    }

    @Test
    fun testFieldDefStmt() {
        assertEquals(
            """
                [FieldDef
                |	(Name): i
                |	(Type): vtl/core/Int
                |	(Mutable): true
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldDefStmt(
                    "i",
                    "vtl/core/Int",
                    true
                )
            )
        )
    }

    @Test
    fun testFieldGetInstanceExpr() {
        assertEquals(
            """
                [FieldGet/Instance
                |	(Name): j
                |	(Instance):
                |	|	[VarGet
                |	|	|	(Name): test
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldGetExpr.Instance(
                    "j",
                    VarGetExpr(
                        "test"
                    )
                )
            )
        )
    }

    @Test
    fun testFieldGetStaticExpr() {
        assertEquals(
            """
                [FieldGet/Static
                |	(Name): j
                |	(Class): ru/DmN/Test
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldGetExpr.Static(
                    "j",
                    "ru/DmN/Test"
                )
            )
        )
    }

    @Test
    fun testFieldGetDynamicExpr() {
        assertEquals(
            """
                [FieldGet/Dynamic
                |	(Name): k
                |	(Instance):
                |	|	[VarGet
                |	|	|	(Name): o
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldGetExpr.Dynamic(
                    "k",
                    VarGetExpr(
                        "o"
                    )
                )
            )
        )
        assertEquals(
            """
                [FieldGet/Dynamic
                |	(Name): k
                |	(Instance):
                |	|	[Value
                |	|	|	(Type): TYPE
                |	|	|	(Value): ru/DmN/Test
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldGetExpr.Dynamic(
                    "k",
                    ValueExpr(
                        ValueExpr.Type.TYPE,
                        "ru/DmN/Test"
                    )
                )
            )
        )
    }

    @Test
    fun testFieldSetInstanceStmt() {
        assertEquals(
            """
                [FieldSet/Instance
                |	(Name): j
                |	(Instance):
                |	|	[VarGet
                |	|	|	(Name): test
                |	|	]
                |	-
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 444
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldSetStmt.Instance(
                    "j",
                    ValueExpr(
                        ValueExpr.Type.INT,
                        444,
                    ),
                    VarGetExpr(
                        "test"
                    )
                )
            )
        )
    }

    @Test
    fun testFieldSetStaticStmt() {
        assertEquals(
            """
                [FieldSet/Static
                |	(Name): j
                |	(Class): ru/DmN/Test
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 555
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldSetStmt.Static(
                    "j",
                    ValueExpr(
                        ValueExpr.Type.INT,
                        555,
                    ),
                    "ru/DmN/Test"
                )
            )
        )
    }

    @Test
    fun testFieldSetDynamicStmt() {
        assertEquals(
            """
                [FieldSet/Dynamic
                |	(Name): k
                |	(Instance):
                |	|	[VarGet
                |	|	|	(Name): o
                |	|	]
                |	-
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 999
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldSetStmt.Dynamic(
                    "k",
                    ValueExpr(
                        ValueExpr.Type.INT,
                        999,
                    ),
                    VarGetExpr(
                        "o"
                    )
                )
            )
        )
        assertEquals(
            """
                [FieldSet/Dynamic
                |	(Name): k
                |	(Instance):
                |	|	[Value
                |	|	|	(Type): TYPE
                |	|	|	(Value): ru/DmN/Test
                |	|	]
                |	-
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 999
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                FieldSetStmt.Dynamic(
                    "k",
                    ValueExpr(
                        ValueExpr.Type.INT,
                        999,
                    ),
                    ValueExpr(
                        ValueExpr.Type.TYPE,
                        "ru/DmN/Test"
                    )
                )
            )
        )
    }

    @Test
    fun testIfExpr() {
        assertEquals(
            """
                [If
                |	(Condition):
                |	|	[Math
                |	|	|	(Operation): LESS
                |	|	|	(Operands):
                |	|	|	|	[VarGet
                |	|	|	|	|	(Name): a
                |	|	|	|	]
                |	|	|	|	[VarGet
                |	|	|	|	|	(Name): b
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	-
                |	(Then):
                |	|	[VarGet
                |	|	|	(Name): b
                |	|	]
                |	-
                |	(Else):
                |	|	[VarGet
                |	|	|	(Name): a
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                IfExpr(
                    MathExpr(
                        MathExpr.Operation.LESS,
                        listOf(
                            VarGetExpr(
                                "a"
                            ),
                            VarGetExpr(
                                "b"
                            )
                        )
                    ),
                    VarGetExpr(
                        "b"
                    ),
                    VarGetExpr(
                        "a"
                    )
                )
            )
        )
    }

    @Test
    fun testIfStmt() {
        assertEquals(
            """
                [If
                |	(Condition):
                |	|	[Value
                |	|	|	(Type): BOOL
                |	|	|	(Value): true
                |	|	]
                |	-
                |	(Then):
                |	|	[Call/Internal
                |	|	|	(Name): vtl/test/foo
                |	|	|	(Arguments):
                |	|	|	-
                |	|	]
                |	-
                |	(Else): (None)
                ]
            """.trimIndent(),
            NodePrinter.print(
                IfStmt(
                    ValueExpr(
                        ValueExpr.Type.BOOL,
                        true
                    ),
                    CallExpr.Internal(
                        "vtl/test/foo",
                        listOf()
                    ),
                    null
                )
            )
        )
        assertEquals(
            """
                [If
                |	(Condition):
                |	|	[Value
                |	|	|	(Type): BOOL
                |	|	|	(Value): false
                |	|	]
                |	-
                |	(Then):
                |	|	[Call/Internal
                |	|	|	(Name): vtl/test/foo
                |	|	|	(Arguments):
                |	|	|	-
                |	|	]
                |	-
                |	(Else):
                |	|	[Call/Internal
                |	|	|	(Name): vtl/test/bar
                |	|	|	(Arguments):
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                IfStmt(
                    ValueExpr(
                        ValueExpr.Type.BOOL,
                        false
                    ),
                    CallExpr.Internal(
                        "vtl/test/foo",
                        listOf()
                    ),
                    CallExpr.Internal(
                        "vtl/test/bar",
                        listOf()
                    )
                )
            )
        )
    }

    @Test
    fun testInterfaceStmt() {
        assertEquals(
            """
                [Interface
                |	(Name): ru/DmN/test/IFoo
                |	(Parents): []
                |	(Context): ru/dmn/test
                |	(Body):
                |	|	[Method
                |	|	|	(Arguments): []
                |	|	|	(ReturnType): vtl/core/Void
                |	|	|	(Body):
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                InterfaceStmt(
                    "ru/DmN/test/IFoo",
                    listOf(),
                    "ru/dmn/test",
                    listOf(
                        MethodStmt(
                            "foo",
                            listOf(),
                            "vtl/core/Void",
                            listOf(),
                        ),
                    )
                )
            )
        )
    }

    @Test
    fun testMathExpr() {
        assertEquals(
            """
                [Math
                |	(Operation): NOT
                |	(Operands):
                |	|	[VarGet
                |	|	|	(Name): flag
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                MathExpr(
                    MathExpr.Operation.NOT,
                    listOf(
                        VarGetExpr(
                            "flag"
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testMethodStmt() {
        assertEquals(
            """
                [Method
                |	(Arguments): []
                |	(ReturnType): vtl/core/Void
                |	(Body):
                |	|	[Return
                |	|	|	(Value): (None)
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                MethodStmt(
                    "foo",
                    listOf(),
                    "vtl/core/Void",
                    listOf(
                        ReturnStmt(
                            null
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testMixinStmt() {
        assertEquals(
            """
                [Mixin
                |	(Name): ru/DmN/test/FooMixin
                |	(Target): ru/DmN/test/FooImpl
                |	(Mixins):
                |	|	[Mixin/Parents
                |	|	|	(Mode): NARROW
                |	|	|	(Value): [ru/DmN/test/IFoo]
                |	|	]
                |	|	[Mixin/Field
                |	|	|	(Mode): NEW
                |	|	|	(Type): vtl/core/Int
                |	|	]
                |	|	[Mixin/Constructor
                |	|	|	(ArgumentTypes): []
                |	|	|	(Body):
                |	|	|	-
                |	|	]
                |	|	[Mixin/Method
                |	|	|	(Mode): DELETE
                |	|	|	(ArgumentTypes): []
                |	|	|	(ReturnType): vtl/core/Void
                |	|	|	(Body):
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                MixinStmt(
                    "ru/DmN/test/FooMixin",
                    "ru/DmN/test/FooImpl",
                    listOf(
                        MixinParentsStmt(
                            Mixin.Parents.Mode.NARROW,
                            listOf(
                                "ru/DmN/test/IFoo"
                            )
                        ),
                        MixinFieldStmt(
                            Mixin.Field.Mode.NEW,
                            "i",
                            "vtl/core/Int",
                        ),
                        MixinConstructorStmt(
                            Mixin.Constructor.Mode.OVERWRITE,
                            listOf(),
                            listOf(),
                        ),
                        MixinMethodStmt(
                            Mixin.Method.Mode.DELETE,
                            "foo",
                            listOf(),
                            "vtl/core/Void",
                            listOf()
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testReturnStmt() {
        assertEquals(
            """
                [Return
                |	(Value): (None)
                ]
            """.trimIndent(),
            NodePrinter.print(
                ReturnStmt(
                    null
                )
            )
        )
        assertEquals(
            """
                [Return
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 12
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                ReturnStmt(
                    ValueExpr(
                        ValueExpr.Type.INT,
                        12
                    )
                )
            )
        )
    }

    @Test
    fun testValueExpr() {
        assertEquals(
            """
                [Value
                |	(Type): NULL
                |	(Value): null
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.NULL,
                    null
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): BOOL
                |	(Value): true
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.BOOL,
                    true
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): INT
                |	(Value): 12
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.INT,
                    12
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): LONG
                |	(Value): 12000000000
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.LONG,
                    12000000000
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): FLOAT
                |	(Value): 21.33
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.FLOAT,
                    21.33f
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): DOUBLE
                |	(Value): 21.00000033
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.DOUBLE,
                    21.00000033
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): STRING
                |	(Value): Hello,\n\tWorld!
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.STRING,
                    "Hello,\n\tWorld!"
                )
            )
        )
        assertEquals(
            """
                [Value
                |	(Type): TYPE
                |	(Value): ru.DmN.Test
                ]
            """.trimIndent(),
            NodePrinter.print(
                ValueExpr(
                    ValueExpr.Type.TYPE,
                    "ru.DmN.Test"
                )
            )
        )
    }

    @Test
    fun testVarDefStmt() {
        assertEquals(
            """
                [VarDef
                |	(Name): k
                |	(Type): vtl/core/Long
                |	(Mutable): true
                |	(Value):
                |	|	[Value
                |	|	|	(Type): LONG
                |	|	|	(Value): 19062007
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                VarDefStmt(
                    "k",
                    "vtl/core/Long",
                    true,
                    ValueExpr(
                        ValueExpr.Type.LONG,
                        19062007
                    )
                )
            )
        )
    }

    @Test
    fun testVarGetExpr() {
        assertEquals(
            """
                [VarGet
                |	(Name): i
                ]
            """.trimIndent(),
            NodePrinter.print(
                VarGetExpr(
                    "i"
                )
            )
        )
    }

    @Test
    fun testVarSetStmt() {
        assertEquals(
            """
                [VarSet
                |	(Name): i
                |	(Value):
                |	|	[Value
                |	|	|	(Type): INT
                |	|	|	(Value): 777
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                VarSetStmt(
                    "i",
                    ValueExpr(
                        ValueExpr.Type.INT,
                        777
                    )
                )
            )
        )
    }

    @Test
    fun testWhileStmt() {
        assertEquals(
            """
                [While
                |	(DoWhile): false
                |	(Condition):
                |	|	[Math
                |	|	|	(Operation): LESS
                |	|	|	(Operands):
                |	|	|	|	[VarGet
                |	|	|	|	|	(Name): i
                |	|	|	|	]
                |	|	|	|	[Value
                |	|	|	|	|	(Type): INT
                |	|	|	|	|	(Value): 100
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	-
                |	(Then):
                |	|	[Block
                |	|	|	(Body):
                |	|	|	|	[Call/Internal
                |	|	|	|	|	(Name): vtl/core/println
                |	|	|	|	|	(Arguments):
                |	|	|	|	|	|	[VarGet
                |	|	|	|	|	|	|	(Name): i
                |	|	|	|	|	|	]
                |	|	|	|	|	-
                |	|	|	|	]
                |	|	|	|	[VarSet
                |	|	|	|	|	(Name): i
                |	|	|	|	|	(Value):
                |	|	|	|	|	|	[Math
                |	|	|	|	|	|	|	(Operation): ADD
                |	|	|	|	|	|	|	(Operands):
                |	|	|	|	|	|	|	|	[VarGet
                |	|	|	|	|	|	|	|	|	(Name): i
                |	|	|	|	|	|	|	|	]
                |	|	|	|	|	|	|	|	[Value
                |	|	|	|	|	|	|	|	|	(Type): INT
                |	|	|	|	|	|	|	|	|	(Value): 1
                |	|	|	|	|	|	|	|	]
                |	|	|	|	|	|	|	-
                |	|	|	|	|	|	]
                |	|	|	|	|	-
                |	|	|	|	]
                |	|	|	-
                |	|	]
                |	-
                ]
            """.trimIndent(),
            NodePrinter.print(
                WhileStmt(
                    false,
                    MathExpr(
                        MathExpr.Operation.LESS,
                        listOf(
                            VarGetExpr(
                                "i"
                            ),
                            ValueExpr(
                                ValueExpr.Type.INT,
                                100
                            )
                        )
                    ),
                    BlockStmt(
                        listOf(
                            CallExpr.Internal(
                                "vtl/core/println",
                                listOf(
                                    VarGetExpr(
                                        "i"
                                    )
                                )
                            ),
                            VarSetStmt(
                                "i",
                                MathExpr(
                                    MathExpr.Operation.ADD,
                                    listOf(
                                        VarGetExpr(
                                            "i"
                                        ),
                                        ValueExpr(
                                            ValueExpr.Type.INT,
                                            1
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}