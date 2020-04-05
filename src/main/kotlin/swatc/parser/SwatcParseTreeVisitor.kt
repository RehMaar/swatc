package swatc.parser

import swatc.ast.*
import swatc.parser.SWatCParser.*

class SwatcParseTreeVisitor : SWatCBaseVisitor<AstNode>() {
    override fun visitProgram(ctx: ProgramContext): AstNode {
        return visit(ctx.block())
    }

    override fun visitBlock(ctx: BlockContext): AstNode {
        val stmts = ctx.statements.map {
            visit(it) as Statement
        }
        return SwatcTree(stmts)
    }

    override fun visitWhileStmt(ctx: WhileStmtContext): AstNode {
        val condition = visit(ctx.cond) as Expression
        val body = visit(ctx.body) as SwatcTree
        return While(condition, body)
    }

    override fun visitIfStmt(ctx: IfStmtContext): AstNode {
        val condition = visit(ctx.cond) as Expression
        val thenC = visit(ctx.thenC) as SwatcTree
        val elseC = ctx.elseC?.let { visit(it) as SwatcTree }
        return If(condition, thenC, elseC)
    }

    override fun visitAssignStmt(ctx: AssignStmtContext): AstNode {
        val identifier = Identifier(ctx.IDENT().text)
        val expression = visit(ctx.expr()) as Expression
        return Assign(identifier, expression)
    }

    override fun visitReassignStmt(ctx: ReassignStmtContext): AstNode {
        val identifier = Identifier(ctx.IDENT().text)
        val expression = visit(ctx.expr()) as Expression
        return Reassign(identifier, expression)
    }

    override fun visitIdentifier(ctx: IdentifierContext): AstNode =
        Identifier(ctx.text)

    override fun visitParExpr(ctx: ParExprContext): AstNode = visit(ctx.exprInPar)

    override fun visitLiteral(ctx: LiteralContext): AstNode =
        Literal(ctx.text.toInt())

    override fun visitBinaryOp(ctx: BinaryOpContext): AstNode {
        val left = visit(ctx.left) as Expression
        val right = visit(ctx.right) as Expression
        val op = BinaryOperation.parse(ctx.op.text)
        return Binary(left, right, op)
    }
}
