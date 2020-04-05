package swatt.ast

/**
 * Marking nodes of our AST.
 */
sealed class AstNode

/**
 * A program in our language is just a list of statements.
 */
data class SlangTree(val statements: List<Statement>) : AstNode()

/**
 * Marking statements of out language.
 */
sealed class Statement : AstNode()

/**
 * `While` statement.
 */
data class While(val condition: Expression, val body: SlangTree) : Statement()

/**
 * `If` statements with optional `else` branch.
 */
data class If(val condition: Expression, val thenC: SlangTree, val elseC: SlangTree?) : Statement()

/**
 * Assign a value to a *fresh* variable.
 */
data class Assign(val identifier: Identifier, val value: Expression) : Statement()

/**
 * Assign a value to an *existing* variable.
 */
data class Reassign(val identifier: Identifier, val value: Expression) : Statement()

/**
 * Marking expression nodes.
 */
sealed class Expression : AstNode()

data class Literal(val value: Int) : Expression()

data class Identifier(val value: String) : Expression()

data class Binary(val left: Expression, val right: Expression, val op: BinaryOperation) : Expression()

enum class BinaryOperation {
    MULT, DIV, PLUS, MINUS, EQ, NEQ, GT, GE, LT, LE;

    companion object {
        fun parse(op: String): BinaryOperation = when (op) {
            "*" -> MULT
            "/" -> DIV
            "+" -> PLUS
            "-" -> MINUS
            "==" -> EQ
            "!=" -> NEQ
            "<=" -> LE
            "<" -> LT
            ">=" -> GE
            ">" -> GT
            else -> throw RuntimeException("No such an operator: $op")
        }
    }
}
