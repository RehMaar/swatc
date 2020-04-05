package swatt

import swatt.parser.SlangParse
import swatt.wat.Watifier


fun main() {
    val p2 = "let i = 3" +
            "let j = 10" +
            "let x = 0" +
            "while (i >! 0) {" +
            "  i = i - 2" +
            "}"

    SlangParse.parse(p2)?.let { println(Watifier.toWat(it)) } ?: println("Unable to parse a program.")
}