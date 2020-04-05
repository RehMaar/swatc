package swatc

import swatc.parser.SwatcParser
import swatc.wat.Watifier


fun main() {
    val p2 =
        "let i = 10" +
                "let j = 10" +
                "let x = 0" +
                "while (i > 0) {" +
                "  i = i - 1" +
                "}"

    println(Watifier.toWat(SwatcParser.parse(p2)))
}