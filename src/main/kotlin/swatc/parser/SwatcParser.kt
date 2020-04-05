package swatc.parser

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.misc.ParseCancellationException
import swatc.ast.SwatcTree
import java.util.*

class SwatcParser {
        companion object {
            fun parse(program: String): SwatcTree? {
                val lexer = SWatCLexer(CharStreams.fromString(program))
                val parser = SWatCParser(CommonTokenStream(lexer))
                parser.removeErrorListeners()
                // Allows some types of mistakes, but still better than the default one.
                parser.errorHandler = BailErrorStrategy()
                return try {
                    SwatcParseTreeVisitor().visit(parser.program()) as SwatcTree
                } catch (e : ParseCancellationException) {
                    null
                }
            }
        }
}