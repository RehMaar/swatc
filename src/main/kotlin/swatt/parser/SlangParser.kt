package swatt.parser

import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.ParseCancellationException
import swatt.ast.SlangTree

class SlangParse {
    companion object {
        fun parse(program: String): SlangTree? {
            val lexer = SlangLexer(CharStreams.fromString(program))
            val parser = SlangParser(CommonTokenStream(lexer))
            parser.removeErrorListeners()
            // Allows some types of mistakes, but still better than the default one.
            parser.errorHandler = BailErrorStrategy()
            return try {
                SlangParseTreeVisitor().visit(parser.program()) as SlangTree?
            } catch (e: ParseCancellationException) {
                null
            }
        }
    }
}