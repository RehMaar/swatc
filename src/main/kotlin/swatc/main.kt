package swatc

import swatc.parser.SwatcParser
import swatc.wat.Watifier


fun main() {
    val p2 = "let i = 3" +
            "let j = 10" +
            "let x = 0" +
            "while (i >! 0) {" +
            "  i = i - 2" +
            "}"

    SwatcParser.parse(p2)?.let { println(Watifier.toWat(it)) } ?: println("Unable to parse a program.")
}