package swatt.parser

import org.junit.Assert.*
import org.junit.Test
import swatt.ast.*

class SlangParserTest {
    @Test
    fun testIf() {
       val p = "let x = 10" +
               "let y = 5" +
               "if (x > 10) {" +
               "  y = 10" +
               "} else {" +
               "  y = 6" +
               "}"

        val expected = SlangTree(listOf(
             Assign(Identifier("x"), Literal(10)),
             Assign(Identifier("y"), Literal(5)),
             If(Binary(Identifier("x"), Literal(10), BinaryOperation.GT),
                 SlangTree(listOf(Reassign(Identifier("y"), Literal(10)))),
                 SlangTree(listOf(Reassign(Identifier("y"), Literal(6))))
             )))

        val result = SlangParse.parse(p)
        assertEquals(expected, result)
    }

    @Test
    fun testWhile() {
        val p = "let x = 10" +
                "while (x < 0) {" +
                "  x = x - 1" +
                "}"

        val expected = SlangTree(listOf(
            Assign(Identifier("x"), Literal(10)),
            While(Binary(Identifier("x"), Literal(0), BinaryOperation.LT),
                SlangTree(listOf(Reassign(Identifier("x"), Binary(Identifier("x"), Literal(1), BinaryOperation.MINUS))))
            )))

        val result = SlangParse.parse(p)
        assertEquals(expected, result)
    }

    @Test
    fun testError() {
        val p = "let x = 'true'"
        val result = SlangParse.parse(p)
        assertTrue(result == null)
    }
}