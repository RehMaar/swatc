package swatc.parser

import org.junit.Assert.*
import org.junit.Test
import swatc.ast.*

class SwatcParserTest {
    @Test
    fun testIf() {
       val p = "let x = 10" +
               "let y = 5" +
               "if (x > 10) {" +
               "  y = 10" +
               "} else {" +
               "  y = 6" +
               "}"

        val expected = SwatcTree(listOf(
             Assign(Identifier("x"), Literal(10)),
             Assign(Identifier("y"), Literal(5)),
             If(Binary(Identifier("x"), Literal(10), BinaryOperation.GT),
                 SwatcTree(listOf(Reassign(Identifier("y"), Literal(10)))),
                 SwatcTree(listOf(Reassign(Identifier("y"), Literal(6))))
             )))

        val result = SwatcParser.parse(p)
        assertEquals(expected, result)
    }

    @Test
    fun testWhile() {
        val p = "let x = 10" +
                "while (x < 0) {" +
                "  x = x - 1" +
                "}"

        val expected = SwatcTree(listOf(
            Assign(Identifier("x"), Literal(10)),
            While(Binary(Identifier("x"), Literal(0), BinaryOperation.LT),
                SwatcTree(listOf(Reassign(Identifier("x"), Binary(Identifier("x"), Literal(1), BinaryOperation.MINUS))))
            )))

        val result = SwatcParser.parse(p)
        assertEquals(expected, result)
    }

    @Test
    fun testError() {
        val p = "let x = 'true'"
        val result = SwatcParser.parse(p)
        print(result)
    }
}