package swatc.parser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import swatc.ast.SwatcTree

class SwatcParser {
    companion object {
        fun parse(program: String): SwatcTree {
            val lexer = SWatCLexer(CharStreams.fromString(program))
            val parser = SWatCParser(CommonTokenStream(lexer))
            return SwatcParseTreeVisitor().visit(parser.program()) as SwatcTree
        }
    }
}