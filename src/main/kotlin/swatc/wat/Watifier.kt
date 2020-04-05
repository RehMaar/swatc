package swatc.wat

import swatc.ast.*

object Watifier {
    private fun assigns(tree: SwatcTree): List<String> = tree.statements.mapNotNull {
        when (it) {
            is Assign -> it.identifier.value
            else -> null
        }
    }

    private fun expressionToWat(expression: Expression, builder: StringBuilder) {
        when (expression) {
            is Literal -> builder.append("(i32.const ${expression.value})")
            is Identifier -> builder.append("(get_local \$${expression.value})")
            is Binary -> {
                builder.append("(")
                operationToWat(expression.op, builder)
                builder.append(" ")
                expressionToWat(expression.left, builder)
                builder.append(" ")
                expressionToWat(expression.right, builder)
                builder.append(")")
            }
        }
    }

    private fun operationToWat(op: BinaryOperation, builder: StringBuilder) {
        when (op) {
            BinaryOperation.MULT -> builder.append("i32.mul")
            BinaryOperation.DIV -> builder.append("i32.div_u")
            BinaryOperation.PLUS -> builder.append("i32.add")
            BinaryOperation.MINUS -> builder.append("i32.sub")
            BinaryOperation.EQ -> builder.append("i32.eq")
            BinaryOperation.NEQ -> builder.append("i32.ne")
            BinaryOperation.LT -> builder.append("i32.lt_u")
            BinaryOperation.GT -> builder.append("i32.gt_u")
            BinaryOperation.LE -> builder.append("i32.le_u")
            BinaryOperation.GE -> builder.append("i32.ge_u")
        }
    }

    private fun assignToWat(identifier: Identifier, value: Expression, builder: StringBuilder) {
        builder.append("(set_local \$${identifier.value} ")
        expressionToWat(value, builder)
        builder.appendln(")")
    }

    private fun ifToWat(condition: Expression, thenC: SwatcTree, elseC: SwatcTree?, builder: StringBuilder) {
        builder.append("(if ")
        expressionToWat(condition, builder)
        builder.appendln("\n(block")
        treeToWat(thenC, builder)
        builder.appendln(")")
        if (elseC != null) {
            builder.appendln("\n(block")
            treeToWat(elseC, builder)
            builder.appendln(")")
        }
        builder.appendln(")")
    }

    private fun whileToWat(condition: Expression, body: SwatcTree, builder: StringBuilder) {
        builder.appendln("(block \$break")
        builder.appendln("(loop \$continue")
        builder.append("(br_if \$break (i32.eq (i32.const 0) ")
        expressionToWat(condition, builder)
        builder.appendln("))")
        treeToWat(body, builder)
        builder.appendln("(br \$continue)))")
    }

    private fun statementToWat(statement: Statement, builder: StringBuilder) = when (statement) {
        is Assign -> assignToWat(statement.identifier, statement.value, builder)
        is Reassign -> assignToWat(statement.identifier, statement.value, builder)
        is If -> ifToWat(statement.condition, statement.thenC, statement.elseC, builder)
        is While -> whileToWat(statement.condition, statement.body, builder)
    }

    private fun treeToWat(tree: SwatcTree, builder: StringBuilder) =
        tree.statements.map { stmt -> statementToWat(stmt, builder) }

    fun toWat(tree: SwatcTree): String {
        val programAssigns = assigns(tree)
        val builder = StringBuilder("(module (func (export \"main\") (param) (result)\n")

        programAssigns.map {
            builder.appendln("(local \$${it} i32)")
        }

        treeToWat(tree, builder)
        builder.appendln("))")
        return builder.toString()
    }

}